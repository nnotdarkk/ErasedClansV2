package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RefuseCommand {

    private final ErasedClans main;

    public RefuseCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.refuse")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer playerManager = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan refuse <clan>");
            return;
        }

        if (playerManager.inClan()) {
            player.sendMessage("§cVous êtes déjà dans un clan");
            return;
        }

        String clanName = args.getArgs(0);
        if (!main.getCacheManager().hasInvitation(player.getUniqueId(), clanName)) {
            player.sendMessage("§cVous n'avez pas d'invitation pour ce clan");
            return;
        }

        Clan clan = main.getClanManager().getClan(clanName);
        main.getCacheManager().removeInvitation(clanName, player.getUniqueId());
        player.sendMessage("§cVous avez refusé l'invitation du clan " + clanName);
        Player owner = Bukkit.getPlayer(clan.getOwner());
        if (owner != null) {
            owner.sendMessage("§c" + player.getName() + " a refusé votre invitation");
        }
    }
}
