package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.inventory.ClanCreateInventory;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class CreateCommand {

    private final ErasedClans main;

    public CreateCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.create")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (clanPlayer.inClan()) {
            player.sendMessage("§cVous êtes déjà dans un clan");
            return;
        }

        ClanCreateInventory createUI = new ClanCreateInventory(player, main);
        createUI.openCreateUI();
    }
}
