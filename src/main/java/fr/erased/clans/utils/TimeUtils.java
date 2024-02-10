package fr.erased.clans.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TimeUtils {

    public static int[] hoursAndMinutesUntilNextMidnight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextMidnight = now.plusDays(1).toLocalDate().atTime(LocalTime.MIDNIGHT);
        Duration duration = Duration.between(now, nextMidnight);

        long totalMinutes = duration.toMinutes();
        int hours = (int) (totalMinutes / 60);
        int minutes = (int) (totalMinutes % 60);

        return new int[] { hours, minutes };
    }

}
