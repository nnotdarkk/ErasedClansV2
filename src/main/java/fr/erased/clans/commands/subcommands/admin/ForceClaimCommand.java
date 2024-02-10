package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.chunks.ClaimedChunks;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public class ForceClaimCommand {

    private final ErasedClans main;

    public ForceClaimCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forceclaim", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 1) {
            player.sendMessage("§c/clan admin forceclaim <clan>");
            return;
        }

        String clan = args.getArgs(0);

        if (main.getFileUtils().getFile("clans", clan).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        Chunk chunk = player.getLocation().getChunk();

        if(main.getChunkManager().getChunks().isClaimed(chunk.toString())){
            player.sendMessage("§cCe chunk est déjà claim !");
            return;
        }

        if(main.getChunkManager().getChunks().getClaimer(chunk.toString()).equals(clan)){
            player.sendMessage("§cCe chunk est déjà claim pas ce clan");
            return;
        }

        ClaimedChunks chunks = main.getChunkManager().getChunks();
        chunks.claimChunk(chunk.toString(), clan);
        main.getChunkManager().saveToFile(chunks);

        player.sendMessage("§aVous avez claim ce chunk pour le clan " + clan + " avec succès");
    }
}
