package fr.erased.clans.leaderboard;

import fr.erased.clans.ErasedClans;
import fr.erased.clans.clans.Clan;
import fr.erased.clans.leaderboard.data.LeaderboardData;

import java.util.*;

public class LeaderboardManager {

    private final ErasedClans main;
    private final Leaderboard leaderboard;
    public LeaderboardManager(ErasedClans main) {
        this.main = main;
        this.leaderboard = new Leaderboard(new HashMap<>(), new LinkedHashMap<>());

        setup();
        new LeaderboardUpdate(this).runTaskTimer(main, 0L, 20L * 60L);
    }

    public void setup(){
        for (String clanName : main.getClanManager().getAllClans()) {
            Clan clan = main.getClanManager().getClan(clanName);

            leaderboard.getAllClanMap().put(clan.getName(), clan.getXp());
        }
    }

    public void updateClan(Clan clan){
        leaderboard.getAllClanMap().remove(clan.getName());
        leaderboard.getAllClanMap().put(clan.getName(), clan.getXp());
    }

    public int getRanking(String clanName) {
        for (int i = 0; i < getAmount(); i++) {
            if (getRanking(i + 1).equalsIgnoreCase(clanName)) {
                return i + 1;
            }
        }
        return 0;
    }

    public String getRanking(int rank) {
        LinkedHashMap<String, Integer> map = leaderboard.getSortedMap();
        if(rank > getAmount()) return null;
        return map.keySet().toArray()[getAmount() - rank].toString();
    }

    public int getScore(String clanName) {
        return leaderboard.getSortedMap().get(clanName);
    }

    public int getScore(int rank) {
        if (rank > getAmount()) return -1;
        return leaderboard.getSortedMap().get(getRanking(rank));
    }

    private int getAmount() {
        return leaderboard.getSortedMap().size();
    }

    public void sort(){
        Date date = new Date();

        HashMap<String, Integer> map = new HashMap<>(leaderboard.getAllClanMap());

        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        Collections.sort(list);

        int rank = list.size();
        for (int num : list) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if(!sortedMap.containsKey(entry.getKey())){
                    if (entry.getValue().equals(num)) {
                        String clanName = entry.getKey();

                        Clan clan = main.getClanManager().getClan(clanName);
                        LeaderboardData leaderboardData = clan.getLeaderboardData();

                        if(leaderboardData.getPlacePeak() == null){
                            leaderboardData.setPlacePeak((rank));
                            leaderboardData.setDatePeak(date);
                            clan.setLeaderboardData(leaderboardData);
                            main.getClanManager().saveClan(clan);
                        } else {
                            if(rank < leaderboardData.getPlacePeak()){
                                leaderboardData.setPlacePeak((rank));
                                leaderboardData.setDatePeak(date);
                                clan.setLeaderboardData(leaderboardData);
                                main.getClanManager().saveClan(clan);
                            }
                        }


                        sortedMap.put(entry.getKey(), num);

                        rank--;
                    }
                }
            }
        }

        leaderboard.setSortedMap(sortedMap);
    }

}
