package fr.erased.clans.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Chunk;
import org.bukkit.Location;

public interface JsonSeriazible {

    Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .create();

    default String toJSON() {
        return GSON.toJson(this);
    }

    static <T extends JsonSeriazible> T fromJSON(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

}
