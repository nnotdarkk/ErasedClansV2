package fr.erased.clans.quests.inventory;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.quests.data.QuestData;
import fr.erased.clans.quests.data.types.BlockQuestData;
import fr.erased.clans.quests.data.streak.DailyStreakData;
import fr.erased.clans.quests.data.types.EntityQuestData;
import fr.erased.clans.quests.data.types.FarmQuestData;
import fr.erased.clans.utils.IntUtils;
import fr.erased.clans.utils.ItemBuilder;
import fr.erased.clans.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class QuestInventory {

    private final ErasedClans main;
    private final Player player;

    public QuestInventory(ErasedClans clans, Player player) {
        this.main = clans;
        this.player = player;
    }

    public void openInventory(){
        Inventory inv = Bukkit.createInventory(null, 45, "Clans - Quêtes");

        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
        Clan clan = main.getClanManager().getClan(clanPlayer.getClan());

        int[] remainingTime = TimeUtils.hoursAndMinutesUntilNextMidnight();

        QuestData data = clan.getQuestData();

        EntityQuestData entityQuestData = data.getEntityQuestData();
        BlockQuestData blockQuestData = data.getBlockQuestData();
        FarmQuestData farmQuestData = data.getFarmQuestData();

        Material farmMaterial = farmQuestData.getEnumeration().getMaterial();

        if(farmMaterial == Material.CARROTS){
            farmMaterial = Material.CARROT;
        }

        if(farmMaterial == Material.BEETROOTS){
            farmMaterial = Material.BEETROOT;
        }

        if(farmMaterial == Material.POTATOES){
            farmMaterial = Material.POTATO;
        }

        DailyStreakData dailyStreakData = data.getDailyStreakData();

        inv.setItem(21, new ItemBuilder(entityQuestData.getEnumeration().getMaterial()).setDisplayName("§eQuête journalière #1")
                .addLore("")
                .addLore(" §8┃ §7Tuez " + entityQuestData.getEnumeration().getNumber() + " " + entityQuestData.getEnumeration().getName() + "s")
                .addLore("")
                .addLore(" §8» §7Temps restant: §a" + (remainingTime[0] - 2) + "h" + remainingTime[1] + "m")
                .addLore(" §8» §7Statut: " + (entityQuestData.isFinished() ? "§aFini" : "§cNon fini"))
                .addLore(" §8» §7Progression: §e" + entityQuestData.getProgression() + "/" + entityQuestData.getEnumeration().getNumber())
                .addLore(" §8» §7Récompense: §b" + main.getQuestsManager().getGainedXp(clan) + " xp " + ((dailyStreakData.getActual() != 0) ? "§8(boost x" + String.format("%.2f", dailyStreakData.getXpBoost()) + ")"  : ""))
                .addLore("").build());
        inv.setItem(22, new ItemBuilder(blockQuestData.getEnumeration().getOneMaterial().get()).setDisplayName("§eQuête journalière #2")
                .addLore("")
                .addLore(" §8┃ §7Cassez " + blockQuestData.getEnumeration().getNumber() + " " + blockQuestData.getEnumeration().getName())
                .addLore("")
                .addLore(" §8» §7Temps restant: §a" + (remainingTime[0] - 2) + "h" + remainingTime[1] + "m")
                .addLore(" §8» §7Statut: " + (blockQuestData.isFinished() ? "§aFini" : "§cNon fini"))
                .addLore(" §8» §7Progression: §e" + blockQuestData.getProgression() + "/" + blockQuestData.getEnumeration().getNumber())
                .addLore(" §8» §7Récompense: §b" + main.getQuestsManager().getGainedXp(clan) + " xp " + ((dailyStreakData.getActual() != 0) ? "§8(boost x" + String.format("%.2f", dailyStreakData.getXpBoost()) + ")" : ""))
                .addLore("").build());
        inv.setItem(23, new ItemBuilder(farmMaterial).setDisplayName("§eQuête journalière #3")
                .addLore("")
                .addLore(" §8┃ §7Récoltez " + farmQuestData.getEnumeration().getNumber() + " " + farmQuestData.getEnumeration().getName())
                .addLore("")
                .addLore(" §8» §7Temps restant: §a" + (remainingTime[0] - 2) + "h" + remainingTime[1] + "m")
                .addLore(" §8» §7Statut: " + (farmQuestData.isFinished() ? "§aFini" : "§cNon fini"))
                .addLore(" §8» §7Progression: §e" + farmQuestData.getProgression() + "/" + farmQuestData.getEnumeration().getNumber())
                .addLore(" §8» §7Récompense: §b" + main.getQuestsManager().getGainedXp(clan) + " xp " + ((dailyStreakData.getActual() != 0) ? "§8(boost x" + String.format("%.2f", dailyStreakData.getXpBoost()) + ")" : ""))
                .addLore("").build());



        inv.setItem(42, new ItemBuilder(Material.CLOCK).setDisplayName("§6Série journalière")
                .addLore(" ")
                .addLore(" §8┃ §7Réalisez chaque jour toutes vos")
                .addLore(" §8┃ §7quêtes pour augmenter votre")
                .addLore(" §8┃ §7série et ainsi maximiser")
                .addLore(" §8┃ §7votre gain d'xp")
                .addLore(" ")
                .addLore(" §8» §7Série actuelle: §a" + dailyStreakData.getActual())
                .addLore(" §8» §7Série précédente: §e" + dailyStreakData.getPrevious())
                .addLore(" §8» §7Série record: §b" + dailyStreakData.getBest())
                .addLore("").build());

        ItemStack vitre = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName(" ").build();
        for (int i : IntUtils.getCorners(inv)){
            inv.setItem(i, vitre);
        }

        inv.setItem(40, new ItemBuilder(Material.DARK_OAK_DOOR).setDisplayName("§cRetour").build());

        player.openInventory(inv);
    }

}
