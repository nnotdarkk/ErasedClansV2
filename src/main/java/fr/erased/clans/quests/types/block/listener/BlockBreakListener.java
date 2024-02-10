package fr.erased.clans.quests.types.block.listener;

import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.quests.QuestsManager;
import fr.erased.clans.quests.data.QuestData;
import fr.erased.clans.quests.data.types.BlockQuestData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;

public class BlockBreakListener implements Listener {

    private final QuestsManager questsManager;

    public BlockBreakListener(QuestsManager questsManager) {
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
        BlockQuestData blockQuestData = questData.getBlockQuestData();
        List<Material> materials = Arrays.asList(blockQuestData.getEnumeration().getMaterial());

        if(blockQuestData.isFinished()){
            return;
        }

        if(!materials.contains(block.getType())){
            return;
        }

        if(questsManager.getErasedClans().getCacheManager().isPlayerPlacedBlock(block)){
            return;
        }

        boolean b = questsManager.increaseQuest(clan, blockQuestData);

        if(!b){
            int difference = blockQuestData.getEnumeration().getNumber() - blockQuestData.getProgression();
            player.sendMessage("§aVous avez cassé un(e) " + blockQuestData.getEnumeration().getName() + " ! Plus que " + difference + " pour terminer votre quête.");
        }
    }
}
