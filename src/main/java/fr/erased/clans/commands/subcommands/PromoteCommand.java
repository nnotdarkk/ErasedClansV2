package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PromoteCommand {

    private final ErasedClans main;

    public PromoteCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.promote")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (!clanPlayer.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (clanPlayer.getRank() != PlayerRank.CHEF) {
            player.sendMessage("§cVous n'avez pas la permission requise. (CHEF)");
            return;
        }

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan promote <joueur>");
            return;
        }

        String target = args.getArgs(0);


        if (player.getName().equals(target)) {
            player.sendMessage("§cVous ne pouvez pas vous promouvoir");
            return;
        }

        ClanPlayer targetClanPlayer = main.getPlayerManager().getPlayer(target);

        if(targetClanPlayer == null){
            player.sendMessage("§cCe joueur n'exsite pas");
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);

        if (!targetClanPlayer.getClan().equals(clanPlayer.getClan())) {
            player.sendMessage("§cCe joueur n'est pas dans votre clan");
            return;
        }

        switch (targetClanPlayer.getRank()) {
            case CHEF:
                break;
            case OFFICIER:
                player.sendMessage("§cCe joueur est déjà officier");
                break;
            case MEMBRE:
                targetClanPlayer.setRank(PlayerRank.OFFICIER);
                main.getPlayerManager().savePlayer(targetClanPlayer);
                player.sendMessage("§a§l» §7Vous avez promu §e" + target + " §7en officier");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été promu officier par §e" + player.getName());
                }
                break;
            case RECRUE:
                targetClanPlayer.setRank(PlayerRank.MEMBRE);
                main.getPlayerManager().savePlayer(targetClanPlayer);
                player.sendMessage("§a§l» §7Vous avez promu §e" + target + " §7en membre");
                if(targetPlayer != null){
                    targetPlayer.sendMessage("§a§l» §7Vous avez été promu membre par §e" + player.getName());
                }
                break;
        }
    }
}
