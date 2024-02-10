package fr.erased.clans.quests.types.block.logic;

import fr.erased.clans.quests.types.block.BreakingQuest;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {

    private final BreakingQuest breakingQuest;

    public BlockListener(BreakingQuest breakingQuest) {
        this.breakingQuest = breakingQuest;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEvent(BlockPlaceEvent e){
        Block block = e.getBlock();
        Material material = block.getType();

        if(!breakingQuest.getMaterialList().contains(material)){
            return;
        }

        breakingQuest.getQuestsManager().getErasedClans().getCacheManager().addPlayerPlacedBlock(block);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEvent(BlockBreakEvent e){
        Block block = e.getBlock();
        breakingQuest.getQuestsManager().getErasedClans().getCacheManager().removePlayerPlacedBlock(block);
    }
}
