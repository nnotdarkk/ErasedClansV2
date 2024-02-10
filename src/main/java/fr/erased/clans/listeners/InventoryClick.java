package fr.erased.clans.listeners;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.clans.inventory.ClanInventory;
import fr.erased.clans.leaderboard.inventory.LeaderboardInventory;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.quests.inventory.QuestInventory;
import fr.erased.clans.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.UUID;

public class InventoryClick implements Listener {

    private final ErasedClans main;

    public InventoryClick(ErasedClans main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) e.getWhoClicked();
        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());

        if(clanPlayer == null){
            return;
        }

        if (e.getView().getTitle().equals("Créer un clan")) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {
                case LIME_TERRACOTTA:
                    player.closeInventory();
                    player.sendMessage(ChatUtils.getCenteredText("§7 "));
                    player.sendMessage(ChatUtils.getCenteredText("§6§lCRÉATION DE CLAN"));
                    player.sendMessage(ChatUtils.getCenteredText("§7Inscrivez le nom de votre clan dans le chat"));
                    player.sendMessage(ChatUtils.getCenteredText("§7('annuler' pour annuler)"));
                    player.sendMessage(ChatUtils.getCenteredText("§7 "));
                    main.getCacheManager().addCreateState(player.getUniqueId());
                    break;

                case RED_TERRACOTTA:
                    player.closeInventory();
                    break;
            }
            return;
        }

        if(clanPlayer.getClan() == null){
            System.out.println("player clicked on clan menu without being in a clan.");
            return;
        }

        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        if(clan == null){
            return;
        }

        if (e.getView().getTitle().startsWith("Clan:")) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {
                case ARROW:
                    player.closeInventory();
                    break;

                case BARRIER:
                    ClanInventory clanInventory = new ClanInventory((Player) e.getWhoClicked(), main, clanPlayer.getClan());
                    clanInventory.quitClanUi();
                    break;

                case BOOK:
                    QuestInventory questInventory = new QuestInventory(main, player);
                    questInventory.openInventory();
                    break;

                case CHEST:
                    player.closeInventory();
                    player.performCommand("clan chest");
                    break;

                case SUNFLOWER:
                    LeaderboardInventory leaderboardInventory = new LeaderboardInventory(main,player);
                    leaderboardInventory.openInventory();
                    break;
            }
            return;
        }

        if (e.getView().getTitle().startsWith("Quitter le clan")) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getType()) {
                case LIME_TERRACOTTA:
                    player.closeInventory();
                    player.sendMessage("§cVous avez quitté le clan " + clanPlayer.getClan());

                    if(clan.getOwner().equals(player.getUniqueId())){
                        main.getClanManager().removeClan(clan);
                        main.getChunkManager().getChunks().removeAllClaimsForClan(clan);
                        for(UUID uuid : clan.getMembers()){
                            ClanPlayer clanUUID = main.getPlayerManager().getPlayer(uuid);
                            clanUUID.setClan(null);
                            clanUUID.setRank(null);
                            main.getPlayerManager().savePlayer(clanUUID);
                        }
                        return;
                    }

                    clan.removeMember(player.getUniqueId());
                    main.getClanManager().saveClan(clan);
                    break;
                case RED_TERRACOTTA:
                    ClanInventory clanInventory = new ClanInventory((Player) e.getWhoClicked(), main, clanPlayer.getClan());
                    clanInventory.openClanUI();
                    break;

            }
            return;
        }

        if(e.getView().getTitle().equalsIgnoreCase("Clans - Quêtes")){
            e.setCancelled(true);

            switch (e.getCurrentItem().getType()){
                case DARK_OAK_DOOR -> {
                    ClanInventory clanInventory = new ClanInventory(player, main, clanPlayer.getClan());
                    clanInventory.openClanUI();
                }

            }
        }

        if(e.getView().getTitle().equalsIgnoreCase("Clans - Classement")){
            e.setCancelled(true);

            switch (e.getCurrentItem().getType()){
                case DARK_OAK_DOOR -> {
                    ClanInventory clanInventory = new ClanInventory(player, main, clanPlayer.getClan());
                    clanInventory.openClanUI();
                }
            }
        }
    }
}
