package fr.erased.clans.utils.json.resolver;

import fr.erased.clans.utils.json.Defaulteable;
import org.bson.Document;

import java.util.Set;

public final class JsonResolver {

    public static String resolveJSON(Defaulteable<?> defaulteable, String json){
        Document defaultDocument = Document.parse(defaulteable.getDefault().toJSON());
        Document actualDocument = Document.parse(json);

        if(defaultDocument.size() >= actualDocument.size()){
            Set<String> allKeys = defaultDocument.keySet();

            for(String key : allKeys){
                if(!actualDocument.containsKey(key)){
                    actualDocument.append(key, defaultDocument.get(key));
                }
            }
        }

        return actualDocument.toJson();
    }

}
