package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.manager.ClanManager;
import fr.erased.clans.utils.FileUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class AddClanXpCommand {

    private final ErasedClans main;

    public AddClanXpCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.addclanxp", permission = "clan.admin")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 2) {
            player.sendMessage("§c/clan admin addclanxp <clan> <xp>");
            return;
        }

        String clan = args.getArgs(0);
        String xp = args.getArgs(1);

        if (!new FileUtils(main).getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Pattern pattern = Pattern.compile("[0-9]+");
        if (!pattern.matcher(xp).matches()) {
            player.sendMessage("§cL'xp doit être un nombre !");
            return;
        }

        int xpInt = Integer.parseInt(xp);

        ClanManager clanManager = new ClanManager(main, clan);
        clanManager.setClanXp(clanManager.getClanXp() + xpInt);
        player.sendMessage("§aVous avez ajouté " + xpInt + "xp au clan " + clan);
    }
}
