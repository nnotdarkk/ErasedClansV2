package fr.erased.clans.quests.types.farm.listeners;

import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.quests.QuestsManager;
import fr.erased.clans.quests.data.QuestData;
import fr.erased.clans.quests.data.types.FarmQuestData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class FarmBreakListener implements Listener {

    private final QuestsManager questsManager;

    public FarmBreakListener(QuestsManager questsManager) {
        this.questsManager = questsManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        ClanPlayer clanPlayer = questsManager.getErasedClans().getPlayerManager().getPlayer(player.getUniqueId());

        if(!clanPlayer.inClan()){
            return;
        }

        Clan clan = questsManager.getErasedClans().getClanManager().getClan(clanPlayer.getClan());

        Block block = e.getBlock();
        QuestData questData = clan.getQuestData();
        FarmQuestData farmQuestData = questData.getFarmQuestData();
        Material material = farmQuestData.getEnumeration().getMaterial();

        if(farmQuestData.isFinished()){
            return;
        }

        if(material != block.getType()){
            return;
        }

        if(farmQuestData.getEnumeration().getBlockData().isPresent()){
            int integer = farmQuestData.getEnumeration().getBlockData().get();

            if((integer != block.getData())){
                return;
            }
        }

        if(questsManager.getErasedClans().getCacheManager().isPlayerPlacedBlock(block)){
            return;
        }

        boolean b = questsManager.increaseQuest(clan, farmQuestData);

        if(!b){
            int difference = farmQuestData.getEnumeration().getNumber() - farmQuestData.getProgression();
            player.sendMessage("§aVous avez récolté un(e) " + farmQuestData.getEnumeration().getName() + " ! Plus que " + difference + " pour terminer votre quête.");
        }
    }
}