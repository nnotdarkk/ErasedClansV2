package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ForceOpenChestCommand {

    private final ErasedClans main;

    public ForceOpenChestCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forceopenchest", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forceopenchest <clan>");
            return;
        }

        String clanName = args.getArgs(0);

        if (main.getFileUtils().getFile("clans", clanName).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTitle().equals("Coffre du clan: " + clanName)) {
                p.closeInventory();
                p.sendMessage("§cCe coffre est entrain de se faire éditer par un administrateur.");
                break;
            }
        }

        Clan clan = main.getClanManager().getClan(clanName);
        main.getChestManager().openChest(player, clan);

        player.sendMessage("§aVous avez ouvert le coffre du clan " + clanName);
    }
}
