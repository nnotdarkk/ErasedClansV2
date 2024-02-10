package fr.erased.clans.cache;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.*;

public class CacheManager {

    private final Map<String, UUID> invitationCache = new HashMap<>();
    private final List<UUID> createState = new ArrayList<>();
    private final List<UUID> fly = new ArrayList<>();
    private final List<UUID> bypassClaims = new ArrayList<>();
    private final List<Block> placedBlocks = new ArrayList<>();

    public boolean hasInvitation(UUID uuid, String name) {
        for (Map.Entry<String, UUID> entry : invitationCache.entrySet()) {
            if (entry.getValue().equals(uuid) && entry.getKey().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeInvitation(String string, UUID uuid){
        invitationCache.remove(string, uuid);
    }

    public void addInvitation(String string, UUID uuid){
        invitationCache.put(string, uuid);
    }

    public boolean isFly(UUID uuid){
        return fly.contains(uuid);
    }

    public void addFly(UUID uuid){
        fly.add(uuid);
    }

    public void removeFly(UUID uuid){
        fly.remove(uuid);
    }

    public boolean isInCreateState(UUID uuid){
        return createState.contains(uuid);
    }

    public void addCreateState(UUID uuid){
        createState.add(uuid);
    }

    public void removeCreateState(UUID uuid){
        createState.remove(uuid);
    }

    public boolean isBypassClaims(UUID uuid){
        return bypassClaims.contains(uuid);
    }

    public void addBypassClaims(UUID uuid){
        bypassClaims.add(uuid);
    }

    public void removeBypassClaims(UUID uuid){
        bypassClaims.remove(uuid);
    }

    public boolean isPlayerPlacedBlock(Block block){
        return placedBlocks.contains(block);
    }

    public void addPlayerPlacedBlock(Block block){
        placedBlocks.add(block);
    }

    public void removePlayerPlacedBlock(Block block){
        placedBlocks.remove(block);
    }

}
