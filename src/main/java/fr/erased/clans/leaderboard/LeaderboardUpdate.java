package fr.erased.clans.leaderboard;

import org.bukkit.scheduler.BukkitRunnable;

public class LeaderboardUpdate extends BukkitRunnable {

    private final LeaderboardManager leaderboardManager;

    public LeaderboardUpdate(LeaderboardManager leaderboardManager) {
        this.leaderboardManager = leaderboardManager;
    }

    @Override
    public void run() {
        leaderboardManager.sort();
    }

}
