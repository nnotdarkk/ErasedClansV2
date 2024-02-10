package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class FlyCommand {

    private final ErasedClans main;

    public FlyCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.fly", permission = "clans.flyclaims")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (clanPlayer.getClan() == null) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (!main.getChunkManager().getChunks().isClaimed(player.getLocation().getChunk().toString())) {
            player.sendMessage("§cVous ne pouvez pas activer le fly dans une zone non claim !");
            return;
        }

        String claimer = main.getChunkManager().getChunks().getClaimer(player.getLocation().getChunk().toString());
        String playerClan = clanPlayer.getClan();

        if (!claimer.equals(playerClan)) {
            player.sendMessage("§cVous ne pouvez pas activer le fly dans une zone claim par un autre clan !");
            return;
        }

        if (main.getCacheManager().isFly(player.getUniqueId())) {
            player.setAllowFlight(false);
            main.getCacheManager().removeFly(player.getUniqueId());
            player.sendMessage("§e§lErased§6§lClans §7» §eVous ne pouvez plus voler dans vos claims");
        } else {
            player.setAllowFlight(true);
            main.getCacheManager().addFly(player.getUniqueId());
            player.sendMessage("§e§lErased§6§lClans §7» §eVous pouvez désormais voler dans vos claims");
        }
    }
}
