package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChestCommand {

    private final ErasedClans main;

    public ChestCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.chest")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (clanPlayer.getClan() == null) {
            player.sendMessage("§cVous n'êtes pas dans un clan !");
            return;
        }

        if (clanPlayer.getRank() == PlayerRank.RECRUE) {
            player.sendMessage("§cVous n'avez pas la permission requise. (MEMBRE)");
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getOpenInventory().getTitle().equals("Coffre du clan: " + clanPlayer.getClan())) {
                player.sendMessage("§cVotre coffre de clan est déjà ouvert par un autre membre du clan.");
                return;
            }
        }

        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());
        main.getChestManager().openChest(player, clan);
    }
}
