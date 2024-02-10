package fr.erased.clans.listeners;

import fr.erased.clans.ErasedClans;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class ClaimsCancels implements Listener {

    private final ErasedClans main;

    public ClaimsCancels(ErasedClans main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getLocation().getChunk();
        if (main.getChunkManager().getChunks().isClaimed(chunk.toString())) {
            UUID uuid = player.getUniqueId();
            String clan = main.getChunkManager().getChunks().getClaimer(chunk.toString());

            if(main.getCacheManager().isBypassClaims(player.getUniqueId())){
                return;
            }

            if (!main.getClanManager().getClan(clan).getMembers().contains(uuid)) {
                e.setCancelled(true);
                player.sendMessage("§cErreur: Cette zone est claim.");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getLocation().getChunk();
        if (main.getChunkManager().getChunks().isClaimed(chunk.toString())) {
            UUID uuid = player.getUniqueId();
            String clan = main.getChunkManager().getChunks().getClaimer(chunk.toString());

            if(main.getCacheManager().isBypassClaims(player.getUniqueId())){
                return;
            }

            if (!main.getClanManager().getClan(clan).getMembers().contains(uuid)) {
                e.setCancelled(true);
                player.sendMessage("§cErreur: Cette zone est claim.");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;

        Player player = e.getPlayer();
        Chunk chunk = e.getClickedBlock().getLocation().getChunk();
        if (main.getChunkManager().getChunks().isClaimed(chunk.toString())) {
            UUID uuid = player.getUniqueId();
            String clan = main.getChunkManager().getChunks().getClaimer(chunk.toString());

            if(main.getCacheManager().isBypassClaims(player.getUniqueId())){
                return;
            }

            if (!main.getClanManager().getClan(clan).getMembers().contains(uuid)) {
                e.setCancelled(true);
                player.sendMessage("§cErreur: Cette zone est claim.");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if(e.getDamager() instanceof Player p){
                if (main.getChunkManager().getChunks().isClaimed(p.getLocation().getChunk().toString())) {
                    e.setCancelled(true);
                    p.sendMessage("§cErreur: Cette zone est claim.");
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileHit(ProjectileHitEvent e){
        if(e.getHitEntity() == null){
            return;
        }

        if(e.getHitEntity() instanceof Player p){
            if (main.getChunkManager().getChunks().isClaimed(p.getLocation().getChunk().toString())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        if (main.getChunkManager().getChunks().isClaimed(e.getLocation().getChunk().toString())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent e) {
        if (main.getChunkManager().getChunks().isClaimed(e.getEntity().getLocation().getChunk().toString())) {
            e.setCancelled(true);
        }
    }
}
