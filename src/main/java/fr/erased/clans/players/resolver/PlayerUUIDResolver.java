package fr.erased.clans.player.resolver;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.FileContentUtils;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerUUIDResolver {

    private final ErasedClans main;

    private final static String FOLDER = "uuidresolver";

    private final Cache<String, UUID> uuidResolverCache;

    public PlayerUUIDResolver(ErasedClans main) {
        this.main = main;

        this.uuidResolverCache = CacheBuilder.newBuilder()
                .concurrencyLevel(50)
                .expireAfterWrite(10L, TimeUnit.MINUTES)
                .build();
    }

    public UUID get(String name){
        UUID uuid = this.uuidResolverCache.getIfPresent(name);
        if(uuid == null){
            uuid = getFromFile(name);

            if(uuid != null){
                this.uuidResolverCache.put(name, uuid);
                return uuid;
            }
        }
        return null;
    }

    public UUID getFromFile(String name){
        File file = new File(main.getDataFolder() + File.separator + FOLDER + File.separator + name + ".json");

        if(!file.exists()){
            return null;
        }

        return UUID.fromString(FileContentUtils.getFileContent(file));
    }

    public void add(String name, UUID uuid){
        File file = new File(main.getDataFolder() + File.separator + FOLDER + File.separator + name + ".json");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileContentUtils.setFileContent(file, uuid.toString());
    }
}
