package fr.erased.clans.quests;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.quests.types.block.BreakingEnum;
import fr.erased.clans.quests.types.block.BreakingQuest;
import fr.erased.clans.quests.data.QuestData;
import fr.erased.clans.quests.data.QuestDataType;
import fr.erased.clans.quests.data.types.BlockQuestData;
import fr.erased.clans.quests.data.streak.DailyStreakData;
import fr.erased.clans.quests.data.types.EntityQuestData;
import fr.erased.clans.quests.data.types.FarmQuestData;
import fr.erased.clans.quests.types.entity.KillEntityEnum;
import fr.erased.clans.quests.types.entity.KillEntityQuest;
import fr.erased.clans.quests.types.farm.FarmEnum;
import fr.erased.clans.quests.types.farm.FarmQuest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.Set;
import java.util.UUID;

public class QuestsManager {

    private final ErasedClans main;

    public QuestsManager(ErasedClans main) {
        this.main = main;

        register();
    }

    public void register(){
        PluginManager pm = main.getServer().getPluginManager();
        new KillEntityQuest(this).registerListeners(pm);
        new BreakingQuest(this).registerListeners(pm);
        new FarmQuest(this).registerListeners(pm);
    }

    public QuestData getNewData(DailyStreakData dailyStreakData){
        EntityQuestData entityQuestData = new EntityQuestData(KillEntityEnum.getRandom(), false, 0);
        BlockQuestData blockQuestData = new BlockQuestData(BreakingEnum.getRandom(), false, 0);
        FarmQuestData farmQuestData = new FarmQuestData(FarmEnum.getRandom(), false, 0);

        if(dailyStreakData == null){
            dailyStreakData = new DailyStreakData(0, 0, 0);
        }

        return new QuestData(entityQuestData, blockQuestData, farmQuestData, dailyStreakData, false);
    }

    public int getGainedXp(Clan clan){
        DailyStreakData dailyStreakData = clan.getQuestData().getDailyStreakData();

        final int def = 300;
        final double boost = dailyStreakData.getXpBoost();

        return (int) (def * boost);
    }

    public boolean increaseQuest(Clan clan, QuestDataType<?> questDataSubType){
        QuestData questData = clan.getQuestData();
        questDataSubType.setProgression(questDataSubType.getProgression() + 1);

        if(questDataSubType.getProgression() >= questDataSubType.getEnumeration().getNumber()){
            clan.addXp(getGainedXp(clan));
            questDataSubType.setFinished(true);

            questData.update(questDataSubType);
            clan.setQuestData(questData);

            Clan checked = checkStreak(clan);

            main.getClanManager().saveClan(checked);

            for(UUID uuid : clan.getMembers()){
                Player player = Bukkit.getPlayer(uuid);

                if(player == null){
                    continue;
                }

                player.sendMessage("§eVotre clan à fini une de ses quêtes journalières ! /clan");
            }
            return true;
        }

        questData.update(questDataSubType);
        clan.setQuestData(questData);

        main.getClanManager().saveClan(clan);
        return false;
    }

    public Clan checkStreak(Clan clan){
        QuestData questData = clan.getQuestData();

        BlockQuestData blockQuestData = questData.getBlockQuestData();
        EntityQuestData entityQuestData = questData.getEntityQuestData();
        FarmQuestData specialQuestData = questData.getFarmQuestData();

        if (!blockQuestData.isFinished()){
            return clan;
        }

        if(!entityQuestData.isFinished()){
            return clan;
        }

        if(!specialQuestData.isFinished()){
            return clan;
        }

        DailyStreakData dailyStreakData = questData.getDailyStreakData();
        int actual = dailyStreakData.getActual();
        dailyStreakData.setActual(actual + 1);

        questData.setDailyStreakData(dailyStreakData);
        questData.setCompletedQuestsToday(true);
        clan.setQuestData(questData);

        for(UUID uuid : clan.getMembers()){
            Player player = Bukkit.getPlayer(uuid);

            if(player == null){
                continue;
            }

            player.sendMessage("§eVotre clan à fini toutes ses quêtes journalières ! /clan");
        }

        return clan;
    }

    public void updateAllClans(){
        Set<String> allClans = main.getClanManager().getAllClans();

        for(String clanString : allClans){
            Clan clan = main.getClanManager().getClan(clanString);

            QuestData questData = clan.getQuestData();
            DailyStreakData dailyStreakData = questData.getDailyStreakData();

            if(!questData.isCompletedQuestsToday()){
                int actual = dailyStreakData.getActual();

                dailyStreakData.setActual(0);

                if(actual != 0){
                    dailyStreakData.setPrevious(actual);
                }

                if(actual > dailyStreakData.getBest()){
                    dailyStreakData.setBest(actual);
                }
            }

            clan.setQuestData(getNewData(dailyStreakData));
            main.getClanManager().saveClan(clan);
        }

    }

    public ErasedClans getErasedClans() {
        return main;
    }
}
