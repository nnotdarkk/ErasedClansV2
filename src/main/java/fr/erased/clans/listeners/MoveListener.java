package fr.erased.clans.listeners;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.chunks.ClaimedChunks;
import fr.erased.clans.players.ClanPlayer;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class MoveListener implements Listener {

    private final ErasedClans main;

    public MoveListener(ErasedClans main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Chunk from = e.getFrom().getChunk();
        Chunk to = Objects.requireNonNull(e.getTo()).getChunk();

        if (from != to) {
            Player player = e.getPlayer();
            ClaimedChunks claimedChunks = main.getChunkManager().getChunks();

            if (!claimedChunks.isClaimed(to.toString())) {
                if (claimedChunks.isClaimed(from.toString())) {
                    player.sendTitle("", "§7Vous sortez du clan §6" + main.getChunkManager().getChunks().getClaimer(from.toString()), 10, 20, 10);
                }

                checkFly(player);

                return;
            }

            if (claimedChunks.isClaimed(to.toString())) {
                ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

                String claimer = claimedChunks.getClaimer(to.toString());
                String playerClan = clanPlayer.getClan();

                if (!claimer.equals(playerClan)) {
                    checkFly(player);
                }

                String claimTo = claimedChunks.getClaimer(to.toString());
                String claimFrom = claimedChunks.getClaimer(from.toString());

                if (!claimTo.equals(claimFrom)) {
                    player.sendTitle("", "§7Vous entrez dans le clan §6" + claimTo, 10, 20, 10);
                }
            }

        }
    }

    @EventHandler
    public void onChangedWorld(PlayerChangedWorldEvent e){
        checkFly(e.getPlayer());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        checkFly(e.getPlayer());
    }

    private void checkFly(Player player){
        if (main.getCacheManager().isFly(player.getUniqueId())) {
            player.setAllowFlight(false);
            main.getCacheManager().removeFly(player.getUniqueId());
            player.sendMessage("§cVotre fly à été désactivé car vous avez quitté votre claim.");
        }
    }
}
