package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class SetBaseCommand {

    private final ErasedClans main;

    public SetBaseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.setbase")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        if (clanPlayer.getClan() == null) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (clanPlayer.getRank() == PlayerRank.RECRUE || clanPlayer.getRank() == PlayerRank.MEMBRE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (OFFICIER)");
            return;
        }

        if (clan.getLevel() < 20) {
            player.sendMessage("§cVous devez être niveau 20 pour définir une base de clan");
            return;
        }

        clan.setBase(player.getLocation());
        main.getClanManager().saveClan(clan);
        player.sendMessage("§a§l» §7Vous avez défini la base de votre clan avec succès");
    }
}
