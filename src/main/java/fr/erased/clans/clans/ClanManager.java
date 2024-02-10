package fr.erased.clans.clans;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.erased.clans.ErasedClans;
import fr.erased.clans.leaderboard.data.LeaderboardData;
import fr.erased.clans.utils.BukkitSerialization;
import fr.erased.clans.utils.json.Defaulteable;
import fr.erased.clans.utils.json.JsonResolver;
import fr.erased.clans.utils.json.JsonSeriazible;
import fr.erased.clans.utils.FileContentUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class ClanManager implements Defaulteable<Clan> {

    private final ErasedClans main;
    private final Cache<String, Clan> clanCache;

    private final static String FOLDER = "clans";
    public ClanManager(ErasedClans main) {
        this.main = main;

        File file = new File(main.getDataFolder() + File.separator + FOLDER);

        if(!file.exists()){
            file.mkdir();
        }

        this.clanCache = CacheBuilder.newBuilder()
                .concurrencyLevel(50)
                .expireAfterWrite(1L, TimeUnit.MINUTES)
                .build();
    }

    public Clan getClan(String name){
        Clan clan = this.clanCache.getIfPresent(name);
        if(clan == null){
            clan = getFromFile(name);

            if(clan != null){
                this.clanCache.put(name, clan);
                return clan;
            }
        }
        return clan;
    }

    private Clan getFromFile(String name){
        File file = getFile(name);

        if(!file.exists()){
            return null;
        }


        String json = FileContentUtils.getFileContent(file);
        String resolved = JsonResolver.resolveJSON(this, json);
        return JsonSeriazible.fromJSON(resolved, Clan.class);
    }

    public void saveClan(Clan clan){
        File file = getFile(clan.getName());

        if(!file.exists()){
            return;
        }

        FileContentUtils.setFileContent(file, clan.toJSON());

        main.getLeaderboardManager().updateClan(clan);
    }

    public void createClan(UUID uuid, String name){
        File file = getFile(name);

        if(file.exists()){
            return;
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Clan clan = getDefault().setOwner(uuid).setName(name);
        FileContentUtils.setFileContent(file, clan.toJSON());

        main.getLeaderboardManager().updateClan(clan);
    }

    public void removeClan(Clan clan){
        File file = getFile(clan.getName());

        if(file.exists()){
            file.delete();
        }

        clanCache.invalidate(clan.getName());
    }

    private File getFile(String name){
        return new File(main.getDataFolder() + File.separator + FOLDER + File.separator + name + ".json");
    }

    public Set<String> getAllClansFiles(){
        File directory = new File(main.getDataFolder() + File.separator + FOLDER);

        File[] files = directory.listFiles();

        if(files == null){
            return new HashSet<>();
        }

        return Stream.of(files)
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public Set<String> getAllClans(){
        Set<String> allClansFiles = getAllClansFiles();
        Set<String> allClans = new HashSet<>();
        for(String string : allClansFiles){
            allClans.add(string.replace(".json", ""));
        }

        return allClans;
    }


    @Override
    public Clan getDefault() {
        return new Clan(null,
                null,
                new ArrayList<>(),
                0,
                new ArrayList<>(),
                null,
                BukkitSerialization.toBase64(Bukkit.createInventory(null, 54)),
                main.getQuestsManager().getNewData(null),
                new LeaderboardData(null, null));
    }
}
