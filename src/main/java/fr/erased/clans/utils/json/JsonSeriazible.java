package fr.erased.clans.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface JsonSeriazible {

    Gson GSON = new GsonBuilder().disableHtmlEscaping().enableComplexMapKeySerialization().serializeNulls().create();

    default String toJSON() {
        return GSON.toJson(this);
    }

    static <T extends JsonSeriazible> T fromJSON(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

}
