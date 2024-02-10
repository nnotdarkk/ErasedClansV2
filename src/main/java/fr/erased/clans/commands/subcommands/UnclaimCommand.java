package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.chunks.ClaimedChunks;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class UnclaimCommand {

    private final ErasedClans main;

    public UnclaimCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.unclaim")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (!clanPlayer.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        if (clanPlayer.getRank() == PlayerRank.RECRUE || clanPlayer.getRank() == PlayerRank.MEMBRE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (OFFICIER)");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        if (!main.getChunkManager().getChunks().isClaimed(chunk.toString())) {
            player.sendMessage("§cCe chunk n'est pas encore claim");
            return;
        }

        String claimer = main.getChunkManager().getChunks().getClaimer(chunk.toString());
        String clanName = clanPlayer.getClan();
        if (!claimer.equals(clanName)) {
            player.sendMessage("§cCe chunk n'est pas claim par votre clan");
            return;
        }

        ClaimedChunks chunks = main.getChunkManager().getChunks();
        chunks.unClaimChunk(chunk.toString());
        main.getChunkManager().saveToFile(chunks);

        Clan clan = main.getClanManager().getClan(clanName);
        clan.unClaimChunk(chunk.toString());
        main.getClanManager().saveClan(clan);

        player.sendMessage("§c§l» §7Vous avez unclaim ce chunk avec succès");
    }
}
