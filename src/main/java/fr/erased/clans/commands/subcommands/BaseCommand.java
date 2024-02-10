package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class BaseCommand {

    private final ErasedClans main;

    public BaseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.base")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        if (clanPlayer.getClan() == null) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (clanPlayer.getRank() == PlayerRank.RECRUE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (MEMBRE)");
            return;
        }

        if (clan.getBase() == null) {
            player.sendMessage("§cVotre clan n'a pas encore de base !");
            return;
        }

        player.teleport(clan.getBase());
    }
}
