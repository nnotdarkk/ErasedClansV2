package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.players.cache.CacheManager;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class BypassClaimsCommand {

    private final ErasedClans main;

    public BypassClaimsCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.bypassclaims", permission = "clan.admin")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 0) {
            player.sendMessage("§c/clan admin bypassclaims");
            return;
        }

        CacheManager cacheManager = main.getCacheManager();

        if(cacheManager.isBypassClaims(player.getUniqueId())){
            cacheManager.removeBypassClaims(player.getUniqueId());
            player.sendMessage("§cVous ne possédez plus le bypass des claims");
        } else {
            cacheManager.addBypassClaims(player.getUniqueId());
            player.sendMessage("§aVous possédez maintenant le bypass des claims");
        }
    }

}
