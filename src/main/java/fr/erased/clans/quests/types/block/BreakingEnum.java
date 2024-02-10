package fr.erased.clans.quests.types.block;

import fr.erased.clans.quests.Numerable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.*;

@Getter @AllArgsConstructor
public enum BreakingEnum implements Numerable {

    STONE(new Material[]{Material.STONE, Material.DEEPSLATE}, 2000, true, "pierre (ou pierre des abimes)"),
    NETHERRACK(Material.NETHERRACK, 2000, true, "netherrack"),
    COAL(new Material[]{Material.COAL_ORE, Material.DEEPSLATE_COAL_ORE}, 200, true, "charbon"),
    COPPER(new Material[]{Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE}, 200, true, "cuivre"),
    IRON(new Material[]{Material.IRON_ORE, Material.DEEPSLATE_IRON_ORE}, 160, true, "fer"),
    REDSTONE(new Material[]{Material.REDSTONE,Material.DEEPSLATE_REDSTONE_ORE}, 120, true, "restone"),
    LAPIS(new Material[]{Material.LAPIS_ORE, Material.DEEPSLATE_LAPIS_ORE}, 120,true, "lapis lazuli"),
    GOLD(new Material[]{Material.GOLD_ORE, Material.DEEPSLATE_GOLD_ORE}, 100,true, "or"),
    DIAMOND(new Material[]{Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE}, 40, true, "diamant"),
    NETHER_GOLD(Material.NETHER_GOLD_ORE, 160, true, "or du nether"),
    EMERALD(new Material[]{Material.EMERALD_ORE, Material.DEEPSLATE_EMERALD_ORE}, 10, true, "emeraude"),
    NETHERITE(Material.ANCIENT_DEBRIS, 4, true, "ancient débris"),

    OAK(Material.OAK_LOG, 120, true, "bûche de chêne"),
    BIRCH(Material.BIRCH_LOG, 120, true, "bûche de bouleau"),
    SPRUCE(Material.SPRUCE_LOG, 120, true, "bûche de sapin"),
    JUNGLE(Material.JUNGLE_LOG, 120, true, "bûche d'acajou"),
    ACACIA(Material.ACACIA_LOG, 120,true, "bûche d'acacia"),
    CHERRY(Material.CHERRY_LOG, 120,true, "bûche de cerisier"),
    MANGROVE(Material.MANGROVE_LOG, 120, true, "bûche de palétuvier"),
    DARK_OAK(Material.DARK_OAK_LOG, 120, true, "bûche de chêne noir"),
    WARPED(Material.WARPED_STEM, 60, true, "tige biscornue"),
    CRIMSON(Material.CRIMSON_STEM, 60,true, "tige carmin");

    private final Material[] material;
    private final int number;
    private final boolean storePlace;
    private final String name;

    BreakingEnum(Material material, int number,boolean storePlace, String name) {
        this.material = new Material[]{material};
        this.number = number;
        this.storePlace = storePlace;
        this.name = name;
    }

    public Optional<Material> getOneMaterial(){
        return Arrays.stream(material).findFirst();
    }

    public static BreakingEnum getFromId(int id){
        for (BreakingEnum killMobsEnum : values()){
            if(killMobsEnum.ordinal() == id){
                return killMobsEnum;
            }
        }
        return null;
    }

    public static BreakingEnum getRandom(){
        int size = values().length - 1;
        Random random = new Random();
        int i = random.nextInt(size);
        return getFromId(i);
    }

    public static List<Material> getMaterials(){
        List<Material> materials = new ArrayList<>();

        for(BreakingEnum breakingEnum : values()){
            if(breakingEnum.storePlace){
                List<Material> a = Arrays.asList(breakingEnum.getMaterial());
                materials.addAll(a);
            }
        }

        return materials;
    }
}
