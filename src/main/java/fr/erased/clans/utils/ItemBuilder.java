package fr.erased.clans.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ItemBuilder {

    private final ItemStack is;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, int meta){
        is = new ItemStack(m, amount, (short) meta);
    }

    public ItemBuilder(Material m, int amount, short meta) {
        is = new ItemStack(m, amount, meta);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder setMaterial(Material material) {
        is.setType(material);
        return this;
    }

    public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        final ItemMeta meta = this.is.getItemMeta();
        meta.setUnbreakable(true);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        try {
            SkullMeta im = (SkullMeta) is.getItemMeta();
            im.setOwner(owner);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemBuilder flag(ItemFlag ItemFlag) {
        ItemMeta im = is.getItemMeta();
        im.addItemFlags(ItemFlag);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLoreWithList(List<String> lore) {
        final List<String> toSet = new ArrayList<String>();
        final ItemMeta meta = this.is.getItemMeta();
        for (final String string : lore) {
            toSet.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        meta.setLore(toSet);
        this.is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLoreWithList(String... lore){
        return setLoreWithList(Arrays.asList(lore));
    }

    public ItemBuilder addLore(String lore) {
        ItemMeta im = is.getItemMeta();
        List<String> loreList = im.getLore();
        if (loreList == null) {
            loreList = new ArrayList<>();
        }
        loreList.add(lore);
        im.setLore(loreList);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setWoolColor(DyeColor color) {
        this.is.setDurability(color.getWoolData());
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemStack build() {
        return this.build(false);
    }

    public ItemStack build(Boolean showItemInfo) {
        ItemMeta im = this.is.getItemMeta();
        if (!showItemInfo) {
            for (ItemFlag flag : ItemFlag.values()) {
                im.addItemFlags(flag);
            }
            this.is.setItemMeta(im);
        }
        return is;
    }
}