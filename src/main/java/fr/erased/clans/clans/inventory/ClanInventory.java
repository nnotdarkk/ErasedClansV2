package fr.erased.clans.clans.inventory;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.players.ClanPlayer;
import fr.erased.clans.utils.ExpUtils;
import fr.erased.clans.utils.IntUtils;
import fr.erased.clans.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class ClanInventory {

    private final Player player;
    private final ErasedClans main;
    private final String name;

    private final Clan clan;

    public ClanInventory(Player player, ErasedClans main, String name) {
        this.main = main;
        this.player = player;
        this.name = name;

        this.clan = main.getClanManager().getClan(name);
    }

    public void openClanUI() {
        Inventory inv = Bukkit.createInventory(null, 45, "Clan: " + name + " [" + clan.getMembers().size() + "/" + clan.getClanMaxMembers() + "]");

        ClanPlayer clanPlayer = main.getPlayerManager().getPlayer(player.getUniqueId());
        UUID ownerId = clan.getOwner();
        String owner = main.getPlayerManager().getPlayer(ownerId).getName();

        int exp = clan.getXp();
        ExpUtils expUtils = new ExpUtils(exp);

        int percent = expUtils.getPercent();
        String progressBar = IntUtils.getProgressBar(percent);

        String nick;
        if (Bukkit.getPlayer(owner) != null) {
            nick = "§a" + owner + " §a§l✓";
        } else {
            nick = "§c" + owner + " §c✘";
        }

        inv.setItem(13, new ItemBuilder(Material.NAME_TAG).setDisplayName("§e" + name).setLoreWithList(Arrays.asList(
                " ",
                " §8┃ §7Statut: §3" + clan.getClanStatus().getFormattedName(),
                " §8┃ §7Chef: §3" + owner,
                " §8┃ §7Grade: §3" + clanPlayer.getRank().getFormattedName(),
                " §8┃ §7Membres: §b" + clan.getMembers().size(),
                " §8┃ §7Niveau: §e" + expUtils.getLevel() + " §7(" + expUtils.getActualExp() + "/" + expUtils.getActualExpToNextLevel() + ")",
                "  " + progressBar + " §8" + percent + "%"
        )).build(false));

        inv.setItem(20, new ItemBuilder(Material.IRON_AXE).setDisplayName("§bJeux de clans").setLoreWithList(Arrays.asList(
                "",
                " §8┃ §7Participez aux jeux de clans",
                " §8┃ §7en jouant à des mini-épreuves",
                " §8┃ §7pour tenter de remporter",
                " §8┃ §7des récompenses inédites.",
                " ",
                "§6▸ §eClique: §fOuvrir"
        )).build(false));

        inv.setItem(21, new ItemBuilder(Material.BOOK).setDisplayName("§6Quêtes").setLoreWithList(Arrays.asList(
                "",
                " §8┃ §7Réalisez des quêtes pour gagner",
                " §8┃ §7de l'expérience et des",
                " §8┃ §7récompenses.",
                " ",
                "§6▸ §eClique: §fOuvrir"
        )).build(false));

        inv.setItem(22,  new ItemBuilder(Material.PLAYER_HEAD, 1, (short) 3)
                .setSkullOwner(owner)
                .setDisplayName("§7Chef: " + nick).build(true));

        inv.setItem(23, new ItemBuilder(Material.CHEST).setDisplayName("§eCoffre de clan").setLoreWithList(Arrays.asList(
                "",
                " §8┃ §7Stockez les objets que vous",
                " §8┃ §7souhaitez et partagez les avec",
                " §8┃ §7votre clan dans un endroit sûr",
                " §8┃ §7et sécurisé.",
                " ",
                "§6▸ §eClique: §fOuvrir"
        )).build(false));

        inv.setItem(24, new ItemBuilder(Material.SUNFLOWER).setDisplayName("§aClassement").setLoreWithList(Arrays.asList(
                "",
                " §8┃ §7Visionnez le classement des",
                " §8┃ §7clans en direct et regardez",
                " §8┃ §7leur progression.",
                " ",
                "§6▸ §eClique: §fOuvrir"
        )).build(false));

        //inv.setItem(41, new ItemBuilder(Material.BARRIER).setDisplayName("§8» §cQuitter le clan").build(false));
        inv.setItem(40, new ItemBuilder(Material.DARK_OAK_DOOR).setDisplayName("§cRetour").build(false));

        ItemStack vitre = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayName(" ").build(false);
        for (int i : IntUtils.getCorners(inv)){
            inv.setItem(i, vitre);
        }

        player.openInventory(inv);
    }

    public void quitClanUi() {
        Inventory inv = Bukkit.createInventory(null, 27, "Quitter le clan " + name + " ?");

        inv.setItem(12, new ItemBuilder(Material.LIME_TERRACOTTA).setDisplayName("§aQuitter le clan").build(false));
        inv.setItem(14, new ItemBuilder(Material.RED_TERRACOTTA).setDisplayName("§cAnnuler").build(false));

        player.openInventory(inv);
    }
}
