package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ForceSetBaseCommand {

    private final ErasedClans main;

    public ForceSetBaseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcesetbase", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcesetbase <clan>");
            return;
        }

        String clanName = args.getArgs(0);

        if (main.getFileUtils().getFile("clans", clanName).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Location location = player.getLocation();

        Clan clan = main.getClanManager().getClan(clanName);
        clan.setBase(location);
        main.getClanManager().saveClan(clan);

        player.sendMessage("§aVous avez défini la nouvelle base du clan " + clanName);
    }
}
