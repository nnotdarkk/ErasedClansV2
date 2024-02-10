package fr.erased.clans.quests.types.farm;

import fr.erased.clans.quests.Numerable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.*;

@Getter @AllArgsConstructor
public enum FarmEnum implements Numerable {

    WHEAT(Material.WHEAT, 1200, 7,false, "blé"),
    PUMPKIN(Material.PUMPKIN, 1200, -1, true, "citrouilles"),
    MELON(Material.MELON, 1200, -1, true, "melons"),
    BEETROOTS(Material.BEETROOTS, 1200, 3, false, "bettraves"),
    CARROTS(Material.CARROTS, 1200, 7, false, "carottes"),
    POTATOES(Material.POTATOES, 1200, 7, false, "patates"),
    SUGAR_CANE(Material.SUGAR_CANE, 1200, -1, true, "cannes à sucre");

    private final Material material;
    private final int number;
    private final int blockData;
    private final boolean storePlace;
    private final String name;

    public static FarmEnum getFromId(int id){
        for (FarmEnum farmEnum : values()){
            if(farmEnum.ordinal() == id){
                return farmEnum;
            }
        }
        return null;
    }

    public Optional<Integer> getBlockData(){
        if(blockData == -1){
            return Optional.empty();
        } else {
            return Optional.of(blockData);
        }
    }

    public static FarmEnum getRandom(){
        int size = values().length - 1;
        Random random = new Random();
        int i = random.nextInt(size);
        return getFromId(i);
    }

    public static List<Material> getMaterials(){
        List<Material> materials = new ArrayList<>();

        for(FarmEnum farmEnum : values()){
            if(farmEnum.storePlace){
                List<Material> a = Collections.singletonList(farmEnum.getMaterial());
                materials.addAll(a);
            }
        }

        return materials;
    }
}
