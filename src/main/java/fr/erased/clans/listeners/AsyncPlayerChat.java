package fr.erased.clans.listeners;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {

    private final ErasedClans main;

    public AsyncPlayerChat(ErasedClans main) {
        this.main = main;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        if (main.getCacheManager().isInCreateState(player.getUniqueId())) {
            e.setCancelled(true);
            if (e.getMessage().equalsIgnoreCase("annuler")) {
                main.getCacheManager().removeCreateState(player.getUniqueId());
                player.sendMessage("§cVous avez annulé la création de votre clan");
                return;
            }

            if (e.getMessage().length() < 3) {
                player.sendMessage("§cLe nom de votre clan doit faire au moins 3 caractères, création annulée");
                main.getCacheManager().removeCreateState(player.getUniqueId());
                return;
            }

            if (e.getMessage().length() > 16) {
                player.sendMessage("§cLe nom de votre clan ne doit pas dépasser 16 caractères, création annulée");
                main.getCacheManager().removeCreateState(player.getUniqueId());
                return;
            }

            if (main.getFileUtils().fileExists("clans", e.getMessage())) {
                player.sendMessage("§cCe nom est déjà utilisé, création annulée");
                main.getCacheManager().removeCreateState(player.getUniqueId());
                return;
            }

            if (e.getMessage().equalsIgnoreCase("null")) {
                player.sendMessage("§cNom de clan invalide, création annulée");
                main.getCacheManager().removeCreateState(player.getUniqueId());
                return;
            }


            if (!e.getMessage().matches("^[a-zA-Z0-9]+$")) {
                player.sendMessage("§cNom de clan invalide (utilisez que des caractères et des chiffres, pas d'espaces ni d'accents), création annulée");
                main.getCacheManager().removeCreateState(player.getUniqueId());
                return;
            }

            main.getCacheManager().removeCreateState(player.getUniqueId());
            player.sendMessage("§aVotre clan a bien été créé ! /clan pour le consulter");

            main.getClanManager().createClan(player.getUniqueId(), e.getMessage());

            Clan clan = main.getClanManager().getClan(e.getMessage());
            clan.addMember(player.getUniqueId());
            main.getClanManager().saveClan(clan);


            ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
            clanPlayer.setClan(e.getMessage());
            clanPlayer.setRank(PlayerRank.CHEF);
            main.getPlayerManager().savePlayer(clanPlayer);

            return;
        }

        String rank = PlaceholderAPI.setPlaceholders(player, "%luckperms_prefix%");
        e.setFormat(rank + "%1$s §8» §7%2$s");

    }
}
