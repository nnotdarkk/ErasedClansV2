package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.utils.ExpUtils;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class ClanInfoCommand {

    private final ErasedClans main;

    public ClanInfoCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.claninfo", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin claninfo <clan>");
            return;
        }

        String clanName = args.getArgs(0);

        if (main.getFileUtils().getFile("clans", clanName).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Clan clan = main.getClanManager().getClan(clanName);

        player.sendMessage("§7Informations sur le clan: §e" + clan.getName());

        player.sendMessage(" §8▪ §7Chef du clan: §e" + main.getPlayerManager().getPlayer(clan.getOwner()).getName());
        player.sendMessage(" §8▪ §7XP du clan: §e" + clan.getXp());
        ExpUtils expUtils = new ExpUtils(clan.getXp());
        player.sendMessage(" §8▪ §7Niveau du clan: §e" + expUtils.getLevel()
                + " §7(" + expUtils.getActualExp() + "/" + expUtils.getActualExpToNextLevel() + ")");
        player.sendMessage(" §8▪ §7Membres du clan: §e");

        for (UUID member : clan.getMembers()) {
            player.sendMessage(" §7- §e" + main.getPlayerManager().getPlayer(member).getName());
        }

        if(clan.getBase() != null){
            Location location = clan.getBase();
            player.sendMessage(" §8▪ §7Base du clan: §ex:"
                    + (int) location.getX() + "/y:"
                    + (int) location.getY() + "/z:"
                    + (int) location.getZ() + " ("
                    + location.getWorld().getName() + ")");
            player.sendMessage(" §8▪ §7Claims du clan: §e");
        } else {
            player.sendMessage("  §8▪ §7Base du clan: §cAucune");
        }


        for (String chunk : clan.getClaims()) {
            player.sendMessage(" §7- §e" + chunk);
        }

        player.sendMessage(" §8▪ §7Statut du clan: §e" + clan.getClanStatus());

    }
}
