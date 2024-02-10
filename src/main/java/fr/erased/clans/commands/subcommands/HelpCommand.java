package fr.erased.clans.commands.subcommands;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.command.CommandSender;
public class HelpCommand {

    private final ErasedClans main;

    private static final int MIN_PAGE = 1;
    private static final int MAX_PAGE = 3;

    public HelpCommand(ErasedClans main) {
        this.main = main;
    }

    @Command(name = "clan.help")
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        int page = 1;

        try {
            page = Integer.parseInt(args.getArgs(0));
        } catch (Exception ignored){
        }

        if(page > MAX_PAGE){
            displayHelp(player, MAX_PAGE);
        }

        if(page < MIN_PAGE){
            displayHelp(player, MIN_PAGE);
        }

        displayHelp(player, page);


    }

    private void displayHelp(CommandSender player, int page){

        player.sendMessage("§7Aide pour ErasedClans (Page " + page +") :");

        if(page == 1){
            player.sendMessage(" §8■ §f/clan base §8(§7Vous téléporte à la base de clan§8)");
            player.sendMessage(" §8■ §f/clan chest §8(§7Vous ouvre le coffre de clan§8)");
            player.sendMessage(" §8■ §f/clan claim §8(§7Claim le chunk dans lequel vous êtes§8)");
            player.sendMessage(" §8■ §f/clan create §8(§7Vous permet de créer un clan§8)");
            player.sendMessage(" §8■ §f/clan demote §9<joueur> §8(§7Rétrograde un membre de votre clan§8)");
        } else if(page == 2){
            player.sendMessage(" §8■ §f/clan fly §8(§7Vous permet de fly dans les claims§8)");
            player.sendMessage(" §8■ §f/clan invite §9<joueur> §8(§7Invite un membre dans le clan§8)");
            player.sendMessage(" §8■ §f/clan join §9<clan> §8(§7Accepte l'invitation dans un clan§8)");
            player.sendMessage(" §8■ §f/clan kick §9<joueur> §8(§7Exclut un joueur de votre clan§8)");
            player.sendMessage(" §8■ §f/clan promote §9<joueur> §8(§7Promeut un joueur§8)");
        } else if(page == 3){
            player.sendMessage(" §8■ §f/clan quit §8(§7Vous permet de quitter votre clan§8)");
            player.sendMessage(" §8■ §f/clan refuse §9<clan> §8(§7Refuse l'invitation dans un clan§8)");
            player.sendMessage(" §8■ §f/clan setbase §8(§7Définit votre base de clan§8)");
            player.sendMessage(" §8■ §f/clan unclaimall §8(§7Unclaim touts les chunks de votre clan§8)");
            player.sendMessage(" §8■ §f/clan unclaim §8(§7Unclaim le chunk dans lequel vous êtes§8)");
        }

        //TODO: finish sah
        player.sendMessage("§7[Page précédente] §6[Page suivante]");
    }

}
