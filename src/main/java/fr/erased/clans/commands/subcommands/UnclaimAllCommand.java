package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.chunks.ClaimedChunks;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class UnclaimAllCommand {

    private final ErasedClans main;

    public UnclaimAllCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.unclaimall")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (!clanPlayer.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        if (clanPlayer.getRank() != PlayerRank.CHEF) {
            player.sendMessage("§cVous n'avez pas la permission requise. (CHEF)");
            return;
        }

        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        ClaimedChunks chunks = main.getChunkManager().getChunks();
        chunks.removeAllClaimsForClan(clan);
        main.getChunkManager().saveToFile(chunks);

        clan.removeAllClaims();
        main.getClanManager().saveClan(clan);

        player.sendMessage("§c§l» §7Vous avez unclaim tous les chunks de votre clan");
    }
}
