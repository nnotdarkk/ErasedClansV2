package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.chunks.ClaimedChunks;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ClaimCommand {

    private final ErasedClans main;

    public ClaimCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.claim")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        if (!clanPlayer.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        if (clanPlayer.getRank() == PlayerRank.RECRUE || clanPlayer.getRank() == PlayerRank.MEMBRE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (OFFICIER)");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();
        if (main.getChunkManager().getChunks().isClaimed(chunk.toString())) {
            player.sendMessage("§cCe chunk est déjà claim");
            return;
        }

        int claimsMax = clan.getClanMaxClaims();
        int claims = clan.getClaims().size();
        if (claims >= claimsMax) {
            player.sendMessage("§cVous avez atteint le nombre maximum de claims");
            return;
        }

        World world = player.getLocation().getWorld();

        if(world != null){
            if(!world.getName().equals("world")){
                player.sendMessage("§cVous ne pouvez pas claim dans un autre monde que celui de la survie");
                return;
            }
        }

        ClaimedChunks chunks = main.getChunkManager().getChunks();
        chunks.claimChunk(chunk.toString(), clanPlayer.getClan());
        main.getChunkManager().saveToFile(chunks);

        clan.claimChunk(chunk.toString());
        main.getClanManager().saveClan(clan);

        player.sendMessage("§a§l» §7Vous avez claim ce chunk avec succès");
    }
}
