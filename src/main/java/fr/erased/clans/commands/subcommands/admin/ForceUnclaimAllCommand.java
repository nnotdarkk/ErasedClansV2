package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.chunks.ClaimedChunks;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceUnclaimAllCommand {

    private final ErasedClans main;

    public ForceUnclaimAllCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forceunclaimall", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forceunclaimall <clan>");
            return;
        }

        String clanName = args.getArgs(0);

        if (main.getFileUtils().getFile("clans", clanName).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Clan clan = main.getClanManager().getClan(clanName);

        ClaimedChunks chunks = main.getChunkManager().getChunks();
        chunks.removeAllClaimsForClan(clan);
        main.getChunkManager().saveToFile(chunks);

        clan.removeAllClaims();
        main.getClanManager().saveClan(clan);

        player.sendMessage("§aVous avez unclaim touts les chunks du clan " + clanName);
    }
}
