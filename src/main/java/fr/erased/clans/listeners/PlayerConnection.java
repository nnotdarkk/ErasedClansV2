package fr.erased.clans.listeners;

import fr.erased.clans.ErasedClans;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnection implements Listener {

    private final ErasedClans main;

    public PlayerConnection(ErasedClans main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("§a+ " + e.getPlayer().getName());

        Player player = e.getPlayer();
        main.getPlayerManager().createPlayer(player.getUniqueId(), player.getName());
        main.getPlayerUUIDResolver().add(player.getName(), player.getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§c- " + e.getPlayer().getName());

        Player player = e.getPlayer();
        main.getCacheManager().removeFly(player.getUniqueId());
        main.getCacheManager().removeCreateState(player.getUniqueId());
        main.getCacheManager().removeBypassClaims(player.getUniqueId());
    }

}
