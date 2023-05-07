package org.highwaycinemaplanner;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSuiteAcceptanceTest {
    HighWayCinemaPlanner sut;
    IMovieCatalog movieMock;

    private final String ROOM_NAME_ONE = "1";
    private final String ROOM_NAME_TWO = "2";
    private final String ROOM_NAME_THREE = "3";
    private final String ROOM_NAME_FOUR = "4";

    private final String CATHEDRAL_MOVIE_NAME = "Cathedral";
    private final Movie cathedralMovie = new Movie(CATHEDRAL_MOVIE_NAME, 45);

    private final Movie microMovie = new Movie(CATHEDRAL_MOVIE_NAME, 5);


    private final Integer STANDARD_CLEANING_SLOT = 15;

    @Before
    public void before() {
        var doubleRoomCinema = new Cinema();

        assertDoesNotThrow(() -> doubleRoomCinema.addRoom(new Room(ROOM_NAME_ONE, STANDARD_CLEANING_SLOT)));
        assertDoesNotThrow(() -> doubleRoomCinema.addRoom(new Room(ROOM_NAME_TWO, STANDARD_CLEANING_SLOT)));
        assertDoesNotThrow(() -> doubleRoomCinema.addRoom(new Room(ROOM_NAME_THREE, STANDARD_CLEANING_SLOT)));
        assertDoesNotThrow(() -> doubleRoomCinema.addRoom(new Room(ROOM_NAME_FOUR, STANDARD_CLEANING_SLOT)));

        movieMock = mock(IMovieCatalog.class);

        sut = new HighWayCinemaPlanner(doubleRoomCinema, movieMock);


        when(movieMock.getMovie(CATHEDRAL_MOVIE_NAME)).thenReturn(cathedralMovie);
        String MICRO_MOVIE_NAME = "Micro";
        when(movieMock.getMovie(MICRO_MOVIE_NAME)).thenReturn(microMovie);
    }

    @Test
    public void whenSchedulingMoviesForAllSlotsInOneDayItShouldScheduleThem() {
        for (LocalDateTime time = LocalDateTime.of(2022, Month.NOVEMBER, 20, 7, 0);
             time.isBefore(LocalDateTime.of(2022, Month.NOVEMBER, 20, 23, 0));
             time = time.plusHours(1)) {
            for (int i = 0; i < 4; i++) {
                Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                        sut.scheduleMovie(CATHEDRAL_MOVIE_NAME, time, false));
            }
        }
        var roomOneSeanses = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(roomOneSeanses);
        Assert.assertEquals(16, roomOneSeanses.size());

        var roomTwoSeanses = sut.listSeanses(ROOM_NAME_TWO);
        Assert.assertNotNull(roomTwoSeanses);
        Assert.assertEquals(16, roomTwoSeanses.size());

        var roomThreeSeanses = sut.listSeanses(ROOM_NAME_THREE);
        Assert.assertNotNull(roomThreeSeanses);
        Assert.assertEquals(16, roomThreeSeanses.size());

        var roomFourSeanses = sut.listSeanses(ROOM_NAME_FOUR);
        Assert.assertNotNull(roomFourSeanses);
        Assert.assertEquals(16, roomFourSeanses.size());
    }

    @Test
    public void whenAllDayIsAllScheduledInAllRoomsAndTryingToScheduleAMovieItShouldNotScheduleAnyOfThem() {
        for (LocalDateTime time = LocalDateTime.of(2022, Month.NOVEMBER, 20, 7, 0);
             time.isBefore(LocalDateTime.of(2022, Month.NOVEMBER, 20, 23, 0));
             time = time.plusHours(1)) {
            for (int i = 0; i < 4; i++) {
                Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                        sut.scheduleMovie(CATHEDRAL_MOVIE_NAME, time, false));
            }
        }

        for (LocalDateTime time = LocalDateTime.of(2022, Month.NOVEMBER, 20, 7, 0);
             time.isBefore(LocalDateTime.of(2022, Month.NOVEMBER, 20, 22, 40));
             time = time.plusMinutes(20)) {
            Assert.assertNotEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                    sut.scheduleMovie(CATHEDRAL_MOVIE_NAME, time, false));
        }
    }
}
