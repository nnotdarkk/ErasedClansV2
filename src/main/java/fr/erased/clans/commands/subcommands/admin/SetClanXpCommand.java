package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class SetClanXpCommand {

    private final ErasedClans main;

    public SetClanXpCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.setclanxp", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 2) {
            player.sendMessage("§c/clan admin setclanxp <clan> <xp>");
            return;
        }

        String clanName = args.getArgs(0);
        String xp = args.getArgs(1);

        if (main.getFileUtils().getFile("clans", clanName).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Pattern pattern = Pattern.compile("[0-9]+");
        if (!pattern.matcher(xp).matches()) {
            player.sendMessage("§cL'xp doit être un nombre !");
            return;
        }

        int xpInt = Integer.parseInt(xp);

        Clan clan = main.getClanManager().getClan(clanName);

        clan.setXp(xpInt);
        main.getClanManager().saveClan(clan);
        player.sendMessage("§aVous avez mis l'xp du clan " + clanName + " à " + xpInt + "xp");
    }
}
