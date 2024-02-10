package fr.erased.clans.quests.types.entity.listeners;

import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.quests.QuestsManager;
import fr.erased.clans.quests.data.QuestData;
import fr.erased.clans.quests.data.types.EntityQuestData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Locale;

public class PlayerKillEntity implements Listener {

    private final QuestsManager questsManager;

    public PlayerKillEntity(QuestsManager questsManager) {
        this.questsManager = questsManager;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        Player player = e.getEntity().getKiller();

        if(player == null){
            return;
        }

        ClanPlayer clanPlayer = questsManager.getErasedClans().getPlayerManager().getPlayer(player.getUniqueId());

        if(!clanPlayer.inClan()){
            return;
        }

        Clan clan = questsManager.getErasedClans().getClanManager().getClan(clanPlayer.getClan());

        QuestData questData = clan.getQuestData();
        EntityQuestData entityQuestData = questData.getEntityQuestData();

        EntityType entityType = e.getEntity().getType();

        if(entityQuestData.isFinished()){
            return;
        }

        if(!entityType.equals(entityQuestData.getEnumeration().getType())){
           return;
        }

        boolean b = questsManager.increaseQuest(clan, entityQuestData);

        if(!b){
            int difference = entityQuestData.getEnumeration().getNumber() - entityQuestData.getProgression();
            player.sendMessage("§aVous avez tué un(e) " + entityQuestData.getEnumeration().getName().toLowerCase(Locale.ROOT) + " ! Plus que " + difference + " pour terminer votre quête.");
        }

    }

}
