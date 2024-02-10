package fr.erased.clans.players;

import fr.erased.clans.utils.json.JsonSeriazible;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor @Accessors(chain = true)
public class ClanPlayer implements JsonSeriazible {

    private UUID uuid;
    private String name;
    private String clan;
    private PlayerRank rank;

    public boolean inClan(){
        return clan != null;
    }

}
