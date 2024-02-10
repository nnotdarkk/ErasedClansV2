package fr.erased.clans.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.json.Defaulteable;
import fr.erased.clans.utils.json.JsonResolver;
import fr.erased.clans.utils.json.JsonSeriazible;
import fr.erased.clans.utils.FileContentUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerManager implements Defaulteable<ClanPlayer> {

    private final ErasedClans main;
    private final Cache<String, ClanPlayer> nameCache;
    private final Cache<UUID, ClanPlayer> uuidCache;

    private final static String FOLDER = "userdata";

    public PlayerManager(ErasedClans main) {
        this.main = main;

        File file = new File(main.getDataFolder() + File.separator + FOLDER);

        if(!file.exists()){
            file.mkdir();
        }

        this.nameCache = CacheBuilder.newBuilder()
                .concurrencyLevel(50)
                .expireAfterWrite(1L, TimeUnit.MINUTES)
                .build();

        this.uuidCache = CacheBuilder.newBuilder()
                .concurrencyLevel(50)
                .expireAfterWrite(1L, TimeUnit.MINUTES)
                .build();
    }

    public ClanPlayer getPlayer(UUID name){
        ClanPlayer clanPlayer = this.uuidCache.getIfPresent(name);
        if(clanPlayer == null){
            clanPlayer = getFromFile(name);

            if(clanPlayer != null){
                this.uuidCache.put(name, clanPlayer);

                return clanPlayer;
            }
        }
        return clanPlayer;
    }

    public ClanPlayer getPlayer(String name){
        ClanPlayer clanPlayer = this.nameCache.getIfPresent(name);
        if(clanPlayer == null){
            clanPlayer = getFromFile(name);

            if(clanPlayer != null){
                this.nameCache.put(name, clanPlayer);
                return clanPlayer;
            }
        }
        return clanPlayer;
    }

    private ClanPlayer getFromFile(String name){
        UUID uuid = main.getPlayerUUIDResolver().get(name);
        File file = getFile(uuid);

        if(!file.exists()){
            return null;
        }

        String json = FileContentUtils.getFileContent(file);
        String resolved = JsonResolver.resolveJSON(this, json);
        return JsonSeriazible.fromJSON(resolved, ClanPlayer.class);
    }

    private ClanPlayer getFromFile(UUID uuid){
        File file = getFile(uuid);

        if(!file.exists()){
            return null;
        }

        String json = FileContentUtils.getFileContent(file);
        String resolved = JsonResolver.resolveJSON(this, json);
        return JsonSeriazible.fromJSON(resolved, ClanPlayer.class);
    }

    public void savePlayer(ClanPlayer clanPlayer){
        File file = getFile(clanPlayer.getUuid());

        if(!file.exists()){
            return;
        }

        FileContentUtils.setFileContent(file, clanPlayer.toJSON());
    }

    public void createPlayer(UUID uuid, String name){
        File file = getFile(uuid);

        if(file.exists()){
            return;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ClanPlayer clanPlayer = getDefault().setUuid(uuid).setName(name);
        FileContentUtils.setFileContent(file, clanPlayer.toJSON());
    }

    private File getFile(UUID uuid){
        return new File(main.getDataFolder() + File.separator + FOLDER + File.separator + uuid + ".json");
    }

    public ClanPlayer getDefault() {
        return new ClanPlayer(null, null, null, null);
    }
}
