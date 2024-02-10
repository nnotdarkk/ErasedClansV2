package fr.erased.clans.commands;

import fr.erased.clans.utils.commands.CommandArgs;
import fr.erased.clans.utils.commands.Completer;

import java.util.ArrayList;
import java.util.List;

public class ClanCompleter {

    @Completer(name = "clan")
    public List<String> onTabComplete(CommandArgs args) {

        List<String> list = new ArrayList<>();

        if (args.getArgs().length == 1) {
            list.add("base");
            list.add("chest");
            list.add("claim");
            list.add("create");
            list.add("demote");
            list.add("fly");
            list.add("help");
            list.add("invite");
            list.add("join");
            list.add("kick");
            list.add("promote");
            list.add("quit");
            list.add("refuse");
            list.add("setbase");
            list.add("unclaim");
            list.add("unclaimall");

            if (args.getPlayer().hasPermission("clans.admin")) {
                list.add("admin");
            }
            return list;
        }

        if (args.getPlayer().hasPermission("clans.admin")) {
            if (args.getArgs(0).equalsIgnoreCase("admin")) {
                if (args.getArgs().length == 2) {
                    list.add("addclanxp");
                    list.add("bypassclaims");
                    list.add("claninfo");
                    list.add("forceclaim");
                    list.add("forcedemote");
                    list.add("forcejoin");
                    list.add("forcekick");
                    list.add("forceopenchest");
                    list.add("forcepromote");
                    list.add("forcesetbase");
                    list.add("forcesetlead");
                    list.add("forceunclaimall");
                    list.add("forceunclaim");
                    list.add("playerinfo");
                    list.add("setclanxp");
                    list.add("teleportbase");

                    return list;
                }
            }
        }

        return null;
    }
}
