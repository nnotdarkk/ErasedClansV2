package fr.erased.clans.chunk;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.utils.FileContentUtils;
import fr.erased.clans.utils.json.JsonSeriazible;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ChunkManager {

    private final ErasedClans main;
    private ClaimedChunks claimedChunks;

    private final static String FOLDER = "chunks";

    public ChunkManager(ErasedClans main) {
        this.main = main;

        File file = new File(main.getDataFolder() + File.separator + FOLDER);

        if(!file.exists()){
            file.mkdir();
        }

        file = getFile();

        if(!file.exists()){
            try {
                file.createNewFile();
                String json = new ClaimedChunks(new HashMap<>()).toJSON();
                FileContentUtils.setFileContent(file, json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.claimedChunks = getFromFile();
    }

    public ClaimedChunks getChunks(){
        return this.claimedChunks;
    }

    public ClaimedChunks getFromFile(){
        File file = getFile();
        String content = FileContentUtils.getFileContent(file);

        return JsonSeriazible.fromJSON(content, ClaimedChunks.class);
    }

    public void saveToFile(ClaimedChunks claimedChunks) {
        String json = claimedChunks.toJSON();

        FileContentUtils.setFileContent(getFile(), json);
        this.claimedChunks = claimedChunks;
    }

    public File getFile(){
        return new File(main.getDataFolder() + File.separator + FOLDER + File.separator + FOLDER + ".json");
    }
}
