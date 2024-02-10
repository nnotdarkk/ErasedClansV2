package fr.erased.clans.commands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.inventory.ClanInventory;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ClanCommand {

    private final ErasedClans main;

    public ClanCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if (args.getArgs().length == 0) {
            if (clanPlayer.inClan()) {
                new ClanInventory(player, main, clanPlayer.getClan()).openClanUI();
                return;
            }

            player.sendMessage("§cVous ne possédez pas de clan, utilisez /clan create pour en créer un");
            //new CreateUI(player, main).openCreateUI();
        }
    }

}
