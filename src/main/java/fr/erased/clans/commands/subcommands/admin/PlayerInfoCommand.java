package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class PlayerInfoCommand {

    private final ErasedClans main;

    public PlayerInfoCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.playerinfo", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin playerinfo <player>");
            return;
        }

        String target = args.getArgs(0);

        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(target);

        if(clanPlayer == null){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }

        player.sendMessage("§7Informations sur §e" + clanPlayer.getName());
        player.sendMessage(" §8▪ §7Clan: §e" + clanPlayer.getClan());
        player.sendMessage(" §8▪ §7Grade: §e" + clanPlayer.getRank().getFormattedName());
    }
}
