package fr.erased.clans.commands.subcommands.admin;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.players.PlayerRank;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ForceJoinCommand {

    private final ErasedClans main;

    public ForceJoinCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.admin.forcejoin", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs().length != 2) {
            player.sendMessage("§c/clan admin forcejoin <clan> <player>");
            return;
        }

        String clanName = args.getArgs(0);
        String target = args.getArgs(1);

        if (main.getFileUtils().getFile("clans", clanName).exists()) {
            player.sendMessage("§cCe clan n'existe pas !");
            return;
        }

        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(target);

        if(clanPlayer == null){
            player.sendMessage("§cCe joueur n'existe pas !");
            return;
        }

        if(clanPlayer.getClan().equals("null")){
            player.sendMessage("§cCe joueur est déjà dans un clan !");
            return;
        }

        Clan clan = main.getClanManager().getClan(clanName);
        clan.addMember(clanPlayer.getUuid());
        main.getClanManager().saveClan(clan);

        clanPlayer.setClan(clan.getName());
        clanPlayer.setRank(PlayerRank.RECRUE);
        main.getPlayerManager().savePlayer(clanPlayer);
    }
}
