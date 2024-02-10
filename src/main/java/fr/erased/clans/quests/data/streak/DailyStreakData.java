package fr.erased.clans.quests.data.streak;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class DailyStreakData {

    private int actual;
    private int previous;
    private int best;

    public double getXpBoost() {
        return 1.0 + 0.1 * actual;
    }

}
