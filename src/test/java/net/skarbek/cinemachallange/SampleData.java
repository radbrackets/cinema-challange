package net.skarbek.cinemachallange;

import net.skarbek.cinemachallange.domain.model.MovieId;
import net.skarbek.cinemachallange.domain.model.RoomId;
import net.skarbek.cinemachallange.domain.model.WeekOfYear;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public class SampleData {

    public static final MovieId MOVIE_ID = new MovieId(UUID.randomUUID().toString());
    public static final MovieId TITANIC_ID = new MovieId(UUID.randomUUID().toString());
    public static final String TITANIC_NAME = "Titanic";
    public static final MovieId SHREK_ID = new MovieId(UUID.randomUUID().toString());
    public static final String SHREK_NAME = "Shrek";
    public static final WeekOfYear WEEK_3_YEAR_2020 = new WeekOfYear(2020, 3);
    public static final Duration DURATION_30M = Duration.ofMinutes(30);
    public static final Duration DURATION_120M = Duration.ofMinutes(120);

    public static final LocalTime CLOCK_08_00 = LocalTime.of(8,0);
    public static final LocalTime CLOCK_22_00 = LocalTime.of(22,0);

    public static final LocalTime CLOCK_12_00 = LocalTime.of(12,0);
    public static final LocalTime CLOCK_14_00 = LocalTime.of(14,0);
    public static final LocalTime CLOCK_14_30 = LocalTime.of(14,30);
    public static final LocalTime CLOCK_15_00 = LocalTime.of(15,0);

    public static final RoomId ROOM_1_ID = new RoomId("room-1");
    public static final String ROOM_1_NAME ="Room 1";
}
