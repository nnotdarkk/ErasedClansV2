package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ForceSetLeadCommand {

    private final ErasedClans main;

    public ForceSetLeadCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcesetlead", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forcesetlead <joueur>");
            return;
        }

        String target = args.getArgs(0);

        ClanPlayer targetManager = main.getPlayerManager().getPlayer(target);

        if(targetManager == null){
            player.sendMessage("§cCe joueur n'existe pas");
            return;
        }
        if(targetManager.getRank() == PlayerRank.CHEF){
            player.sendMessage("§cCe joueur est déjà le chef du clan !");
            return;
        }

        Clan targetClan = main.getClanManager().getClan(targetManager.getClan());
        for(UUID uuid : targetClan.getMembers()){
            ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(uuid);

            if(clanPlayer.getRank() == PlayerRank.CHEF){
                clanPlayer.setRank(PlayerRank.OFFICIER);
                main.getPlayerManager().savePlayer(clanPlayer);
                break;
            }
        }

        targetManager.setRank(PlayerRank.CHEF);
        main.getPlayerManager().savePlayer(targetManager);

        player.sendMessage("§aVous avez mis " + target + " en tant que chef de ce clan. Attention, l'ancien chef de clan est passé officier");
    }
}