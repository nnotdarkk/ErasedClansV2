package fr.erased.clans.leaderboard.inventory;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.leaderboard.LeaderboardManager;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.ExpUtils;
import fr.erased.clans.utils.IntUtils;
import fr.erased.clans.utils.ItemBuilder;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LeaderboardInventory {

    private final ErasedClans main;
    private final Player player;

    public LeaderboardInventory(ErasedClans clans, Player player) {
        this.main = clans;
        this.player = player;
    }

    public void openInventory(){
        Inventory inv = Bukkit.createInventory(null, 45, "Clans - Classement");

        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        LeaderboardManager leaderboardManager = main.getLeaderboardManager();

        int a = 11;
        for (int i = 1; i < 4; i++) {
            String ranking = leaderboardManager.getRanking(i);

            if(ranking == null){
                inv.setItem(a, new ItemBuilder(Material.BARRIER).setDisplayName("§cAucun").build());
                a++;
                continue;
            }

            Clan rankingClan = main.getClanManager().getClan(ranking);

            ClanPlayer clanTarget = main.getPlayerManager().getPlayer(rankingClan.getOwner());

            ExpUtils expUtils = new ExpUtils(rankingClan.getXp());

            inv.setItem(a, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(clanTarget.getName())
                    .setDisplayName("§6" + rankingClan.getName())
                    .addLore(" ")
                    .addLore(" §8┃ §7Niveau: §e" + expUtils.getLevel())
                    .addLore(" §8┃ §7Expérience: §b" + expUtils.getActualExp() + "/" + expUtils.getActualExpToNextLevel())
                    .addLore(" ").build());

            a++;
        }

        inv.setItem(20, new ItemBuilder(Material.GOLD_INGOT).setDisplayName("§ePlace #1").build());
        inv.setItem(21, new ItemBuilder(Material.IRON_INGOT).setDisplayName("§7Place #2").build());
        inv.setItem(22, new ItemBuilder(Material.COPPER_INGOT).setDisplayName("§6Place #3").build());

        ItemStack vitre = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName(" ").build();
        for (int i : IntUtils.getCorners(inv)){
            inv.setItem(i, vitre);
        }

        inv.setItem(15, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§eInformation")
                .addLore("")
                .addLore(" §8┃ §7Ce classement s'actualise toutes")
                .addLore(" §8┃ §7les minutes et affiche les trois")
                .addLore(" §8┃ §7meilleurs clans du serveur")
                .addLore(" §8┃ §7selon leur exp.")
                .addLore("").build());


        String placePeak = "§cAucun";
        if(clan.getLeaderboardData().getPlacePeak() != null){
            placePeak = "§b#" + clan.getLeaderboardData().getPlacePeak();
        }

        String date = toRealDate(clan.getLeaderboardData().getDatePeak().toString());

        inv.setItem(24, new ItemBuilder(Material.FIREWORK_STAR).setDisplayName("§eVotre classement")
                .addLore("")
                .addLore(" §8» §7Votre classement: §6#" + leaderboardManager.getRanking(clan.getName()))
                .addLore("")
                .addLore(" §8» §7Meilleur classement: " + placePeak)
                .addLore(" §8» §7Date: §3" + date)
                .addLore("").build());

        inv.setItem(40, new ItemBuilder(Material.DARK_OAK_DOOR).setDisplayName("§cRetour").build());

        player.openInventory(inv);
    }

    @SneakyThrows
    public String toRealDate(String dateString) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date date = inputDateFormat.parse(dateString);

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return outputDateFormat.format(date);
    }
}
