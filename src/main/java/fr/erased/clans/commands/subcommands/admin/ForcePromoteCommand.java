package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ForcePromoteCommand {

    private final ErasedClans main;

    public ForcePromoteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcepromote", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcepromote <player>");
            return;
        }

        String promoted = args.getArgs(0);

        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(promoted);

        if(clanPlayer == null){
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(promoted);

        switch (clanPlayer.getRank()) {
            case CHEF:
                break;
            case OFFICIER:
                player.sendMessage("§cCe joueur est déjà officier");
                break;
            case MEMBRE:
                clanPlayer.setRank(PlayerRank.OFFICIER);
                main.getPlayerManager().savePlayer(clanPlayer);
                player.sendMessage("§a§l» §7Vous avez promu §e" + promoted + " §7en officier");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été promu officier par §e" + player.getName());
                }
                break;
            case RECRUE:
                clanPlayer.setRank(PlayerRank.MEMBRE);
                main.getPlayerManager().savePlayer(clanPlayer);
                player.sendMessage("§a§l» §7Vous avez promu §e" + promoted + " §7en membre");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été promu membre par §e" + player.getName());
                }
                break;
        }
    }
}
