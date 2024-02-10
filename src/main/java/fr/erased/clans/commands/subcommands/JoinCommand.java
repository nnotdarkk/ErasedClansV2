package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JoinCommand {

    private final ErasedClans main;

    public JoinCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.join")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan join <clan>");
            return;
        }

        if (clanPlayer.inClan()) {
            player.sendMessage("§cVous êtes déjà dans un clan");
            return;
        }

        String clanName = args.getArgs(0);
        if (!main.getCacheManager().hasInvitation(player.getUniqueId(), clanName)) {
            player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
            return;
        }

        main.getCacheManager().removeInvitation(clanName, player.getUniqueId());

        Clan targetClan = main.getClanManager().getClan(clanName);
        targetClan.addMember(player.getUniqueId());
        main.getClanManager().saveClan(targetClan);

        clanPlayer.setClan(clanName);
        clanPlayer.setRank(PlayerRank.RECRUE);
        main.getPlayerManager().savePlayer(clanPlayer);


        player.sendMessage("§aVous avez rejoint le clan " + clanName);
        Player player1 = Bukkit.getPlayer(targetClan.getOwner());
        if (player1 != null) {
            player1.sendMessage("§a" + player.getName() + " a accepté votre invitation");
        }
    }

}
