package fr.erased.clans;

import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClanPlaceholders extends PlaceholderExpansion {

    private final ErasedClans main;

    public ClanPlaceholders(ErasedClans main) {
        this.main = main;
    }

    public @NotNull String getIdentifier() {
        return "clans";
    }

    public @NotNull String getAuthor() {
        return "Karam";
    }

    public @NotNull String getVersion() {
        return "1.0.0";
    }

    public String onPlaceholderRequest(Player player, String params) {
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        if (params.equalsIgnoreCase("name")) {
            if (!clanPlayer.inClan()) {
                return "§cAucun";
            }
            return clanPlayer.getClan();
        }

        if (params.equalsIgnoreCase("xp")) {
            return String.valueOf(clan.getXp());
        }

        if (params.equalsIgnoreCase("level")) {
            return String.valueOf(clan.getLevel());
        }

        return null;
    }
}
