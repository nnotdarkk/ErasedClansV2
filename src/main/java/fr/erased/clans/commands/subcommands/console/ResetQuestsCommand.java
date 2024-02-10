package fr.erased.clans.commands.subcommands.console;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.commands.Command;
import fr.erased.clans.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ResetQuestsCommand {

    private final ErasedClans erasedClans;

    public ResetQuestsCommand(ErasedClans erasedClans) {
        this.erasedClans = erasedClans;
    }

    @Command(name = "clan.console.resetquests", permission = "clan.admin", inGameOnly = false)
    public void onCommand(CommandArgs args){
        if(args.getSender() instanceof Player){
            return;
        }

        erasedClans.getQuestsManager().updateAllClans();
    }
}
