package fr.erased.clans.clan;

import fr.erased.clans.leaderboard.data.LeaderboardData;
import fr.erased.clans.quests.data.QuestData;
import fr.erased.clans.utils.ExpUtils;
import fr.erased.clans.utils.json.JsonSeriazible;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

@Getter @Setter @AllArgsConstructor @Accessors(chain = true)
public class Clan implements JsonSeriazible {

    private String name;
    private UUID owner;
    private List<UUID> members;
    private int xp;
    private List<String> claims;
    private Location base;
    private String chest;

    private QuestData questData;
    private LeaderboardData leaderboardData;

    public void unClaimChunk(String chunk){
        claims.remove(chunk);
    }

    public void claimChunk(String chunk){
        claims.add(chunk);
    }

    public void removeAllClaims() {
        claims.clear();
    }

    public int getLevel(){
        return new ExpUtils(xp).getLevel();
    }

    public int getClanMaxClaims() {
        return getClanStatus().getMaxClaims();
    }

    public int getClanMaxMembers() {
        return getClanStatus().getMaxMembers();
    }

    public ClanStatus getClanStatus() {
        ExpUtils expUtils = new ExpUtils(xp);
        int level = expUtils.getLevel();

        if (level >= 100) {
            return ClanStatus.EMPIRE;
        }

        if (level >= 80) {
            return ClanStatus.CITADELLE;
        }

        if (level >= 50) {
            return ClanStatus.ROYAUME;
        }

        return ClanStatus.VILLAGE;
    }

    public void addMember(UUID uuid){
        members.add(uuid);
    }

    public void removeMember(UUID uuid){
        members.remove(uuid);
    }

    public void addXp(int added){
        xp = xp + added;
    }

}
