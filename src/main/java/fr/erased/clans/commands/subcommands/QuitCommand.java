package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.inventory.ClanInventory;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class QuitCommand {

    private final ErasedClans main;

    public QuitCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.quit")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (!clanPlayer.inClan()) {
            player.sendMessage("§cVous n'êtes pas dans un clan");
            return;
        }

        ClanInventory clanUI = new ClanInventory(player, main, clanPlayer.getClan());
        clanUI.quitClanUi();
    }
}
