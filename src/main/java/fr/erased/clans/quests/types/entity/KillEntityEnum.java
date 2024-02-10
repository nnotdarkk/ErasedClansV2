package fr.erased.clans.quests.types.entity;

import fr.erased.clans.quests.Numerable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Random;

@Getter @AllArgsConstructor
public enum KillEntityEnum implements Numerable {

    WITHER_SKELETON(EntityType.WITHER_SKELETON, 12, Material.WITHER_SKELETON_SPAWN_EGG, "Squelette du wither"),
    STRAY(EntityType.STRAY, 20, Material.STRAY_SPAWN_EGG, "Vagabond"),
    HUSK(EntityType.HUSK, 20, Material.HUSK_SPAWN_EGG, "Zombie momifié"),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER, 40, Material.ZOMBIE_VILLAGER_SPAWN_EGG, "Zombie Villageois"),
    SKELETON_HORSE(EntityType.SKELETON_HORSE, 4, Material.SKELETON_HORSE_SPAWN_EGG, "Cheval squelette"),
    ZOMBIE_HORSE(EntityType.ZOMBIE_HORSE, 4, Material.ZOMBIE_HORSE_SPAWN_EGG, "Cheval zombie"),
    CREEPER(EntityType.CREEPER, 40, Material.CREEPER_SPAWN_EGG, "Creeper"),
    SKELETON(EntityType.SKELETON, 40, Material.SKELETON_SPAWN_EGG, "Squelette"),
    SPIDER(EntityType.SPIDER, 40, Material.SPIDER_SPAWN_EGG, "Araignée"),
    ZOMBIE(EntityType.ZOMBIE, 40, Material.ZOMBIE_SPAWN_EGG, "Zombie"),
    SLIME(EntityType.SLIME, 20, Material.SLIME_SPAWN_EGG, "Slime"),
    GHAST(EntityType.GHAST, 4, Material.GHAST_SPAWN_EGG, "Ghast"),
    ZOMBIFIED_PIGLIN(EntityType.ZOMBIFIED_PIGLIN, 20, Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, "Piglin zombifié"),
    ENDERMAN(EntityType.ENDERMAN, 20, Material.ENDERMAN_SPAWN_EGG, "Enderman"),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, 20, Material.CAVE_SPIDER_SPAWN_EGG, "Araignée venimeuse"),
    BLAZE(EntityType.BLAZE, 20, Material.BLAZE_SPAWN_EGG, "Blaze"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE,  20, Material.MAGMA_CUBE_SPAWN_EGG, "Bloc de magma"),
    BAT(EntityType.BAT, 10, Material.BAT_SPAWN_EGG, "Chauve-souris"),
    WITCH(EntityType.WITCH, 4, Material.WITCH_SPAWN_EGG, "Sorcière"),
    PHANTOM(EntityType.PHANTOM, 12, Material.PHANTOM_SPAWN_EGG, "Phantome"),
    DROWNED(EntityType.DROWNED, 40, Material.DROWNED_SPAWN_EGG, "Noyé"),
    PILLAGER(EntityType.PILLAGER, 8, Material.PILLAGER_SPAWN_EGG, "Pillard"),
    HOGLIN(EntityType.HOGLIN, 20, Material.HOGLIN_SPAWN_EGG, "Hoglin"),
    PIGLIN(EntityType.PIGLIN, 20, Material.PIGLIN_SPAWN_EGG, "Piglin"),
    STRIDER(EntityType.STRIDER, 20, Material.STRIDER_SPAWN_EGG, "Arpenteur"),
    ZOGLIN(EntityType.ZOGLIN, 20, Material.ZOGLIN_SPAWN_EGG, "Zoglin"),
    PIGLIN_BRUTE(EntityType.PIGLIN_BRUTE, 12, Material.PIGLIN_BRUTE_SPAWN_EGG, "Piglin barbare"),
    ENDERMITE(EntityType.ENDERMITE, 20, Material.ENDERMITE_SPAWN_EGG, "Endermite");

    private final EntityType type;
    private final int number;
    private final Material material;
    private final String name;

    public static KillEntityEnum getFromId(int id){
        for (KillEntityEnum killMobsEnum : values()){
            if(killMobsEnum.ordinal() == id){
                return killMobsEnum;
            }
        }
        return null;
    }

    public static KillEntityEnum getRandom(){
        int size = values().length - 1;
        Random random = new Random();
        int i = random.nextInt(size);
        return getFromId(i);
    }
}
