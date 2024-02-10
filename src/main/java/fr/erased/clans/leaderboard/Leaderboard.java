package fr.erased.clans.leaderboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter @AllArgsConstructor
public class Leaderboard {

    private Map<String, Integer> allClanMap;
    private LinkedHashMap<String, Integer> sortedMap;

}
