package fr.erased.clans.chunk;

import fr.erased.clans.clan.Clan;
import fr.erased.clans.utils.json.JsonSeriazible;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Chunk;

import java.util.List;
import java.util.Map;

@Getter @Setter @AllArgsConstructor
public class ClaimedChunks implements JsonSeriazible {

    private Map<String, String> claimedChunks;

    public boolean isClaimed(String chunk){
        return claimedChunks.containsKey(chunk);
    }

    public String getClaimer(String chunk){
        return claimedChunks.get(chunk);
    }

    public void unClaimChunk(String chunk){
        claimedChunks.remove(chunk);
    }

    public void claimChunk(String chunk, String clan){
        claimedChunks.put(chunk, clan);
    }

    public void removeAllClaimsForClan(Clan clan){
        List<String> clanClaims = clan.getClaims();

        for(String chunk : clanClaims){
            claimedChunks.remove(chunk);
        }
    }

}
