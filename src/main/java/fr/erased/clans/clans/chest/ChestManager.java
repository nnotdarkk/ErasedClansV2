package fr.erased.clans.clans.chest;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.utils.BukkitSerialization;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChestManager {

    private final ErasedClans main;
    public ChestManager(ErasedClans main) {
        this.main = main;
    }

    public void openChest(Player player, Clan clan) {
        String chest = clan.getChest();
        Inventory base64inv = BukkitSerialization.fromBase64(chest);
        Inventory inv = Bukkit.createInventory(null, 54, "Coffre du clan: " + clan.getName());
        inv.setContents(base64inv.getContents());
        addGlass(inv, clan);

        player.openInventory(inv);
    }

    private void addGlass(Inventory inv, Clan clan) {

        int level = clan.getLevel();

        if (level < 100) {
            for (int i = 45; i < 54; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 100").build(false));
            }
        }

        if (level < 80) {
            for (int i = 36; i < 45; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 80").build(false));
            }
        }

        if (level < 50) {
            for (int i = 27; i < 36; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 50").build(false));
            }
        }

        if (level < 40) {
            for (int i = 18; i < 27; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 40").build(false));
            }
        }

        if (level < 20) {
            for (int i = 9; i < 18; i++) {
                inv.setItem(i, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§cDisponible au niveau 20").build(false));
            }
        }
    }

    public Inventory removeGlass(Inventory inv) {
        for (int i = 9; i < 54; i++) {
            if (inv.getItem(i) == null) continue;
            if (inv.getItem(i).getItemMeta() == null) continue;

            if (inv.getItem(i).getItemMeta().getDisplayName().startsWith("§c")) {
                inv.setItem(i, null);
            }
        }
        return inv;
    }
}
