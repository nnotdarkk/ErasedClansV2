package fr.erased.clans.leaderboard.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor
public class LeaderboardData {

    private Integer placePeak;
    private Date datePeak;

}
