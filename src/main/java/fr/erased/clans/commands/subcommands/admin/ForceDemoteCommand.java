package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ForceDemoteCommand {

    private final ErasedClans main;

    public ForceDemoteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcedemote", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcedemote <player>");
            return;
        }

        String target = args.getArgs(0);


        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(target);

        if(clanPlayer == null){
            player.sendMessage("§cCe joueur n'existe pas !");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);

        switch (clanPlayer.getRank()) {
            case CHEF:
                break;
            case OFFICIER:
                clanPlayer.setRank(PlayerRank.MEMBRE);
                main.getPlayerManager().savePlayer(clanPlayer);
                player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target + " §7en membre");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été dé-promu membre par §e" + player.getName());
                }
                break;
            case MEMBRE:
                clanPlayer.setRank(PlayerRank.RECRUE);
                main.getPlayerManager().savePlayer(clanPlayer);
                player.sendMessage("§a§l» §7Vous avez dé-promu §e" + target + " §7en recrue");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été dé-promu recrue par §e" + player.getName());
                }
                break;
            case RECRUE:
                player.sendMessage("§cCe joueur est déjà recrue");
                break;
        }
    }
}
