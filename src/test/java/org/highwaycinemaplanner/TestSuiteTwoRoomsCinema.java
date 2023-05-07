package org.highwaycinemaplanner;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSuiteTwoRoomsCinema {
    HighWayCinemaPlanner sut;
    IMovieCatalog movieMock;

    private final LocalDateTime SEANS_DATE_ONE = LocalDateTime.of(2022, Month.NOVEMBER, 20, 15, 0);

    private final String ROOM_NAME_ONE = "1";
    private final String ROOM_NAME_TWO = "2";

    private  final String SMURFS_MOVIE_NAME = "Smurfs";
    private final Movie smurfsMovie = new Movie(SMURFS_MOVIE_NAME, 30);

    private final String AVATAR_MOVIE_NAME = "Avatar";
    private final Movie avatarMovie = new Movie(AVATAR_MOVIE_NAME, 150);

    private final Integer STANDARD_CLEANING_SLOT = 15;

    @Before
    public void before() {
        var doubleRoomCinema = new Cinema();

        assertDoesNotThrow(() -> doubleRoomCinema.addRoom(new Room(ROOM_NAME_ONE, STANDARD_CLEANING_SLOT)));
        assertDoesNotThrow(() -> doubleRoomCinema.addRoom(new Room(ROOM_NAME_TWO, STANDARD_CLEANING_SLOT)));

        movieMock = mock(IMovieCatalog.class);

        sut = new HighWayCinemaPlanner(doubleRoomCinema, movieMock);

        String NOT_EXISTING_MOVIE = "Not existing movie";
        when(movieMock.getMovie(NOT_EXISTING_MOVIE)).thenReturn(null);
        when(movieMock.getMovie(SMURFS_MOVIE_NAME)).thenReturn(smurfsMovie);
        when(movieMock.getMovie(AVATAR_MOVIE_NAME)).thenReturn(avatarMovie);
    }

    @Test
    public void whenTryingToCreateCinemaWithTwoRoomsWithSameNameShouldThrow() {
        var singleRoomCinema = new Cinema();

        assertDoesNotThrow(() -> singleRoomCinema.addRoom(new Room(ROOM_NAME_ONE, STANDARD_CLEANING_SLOT)));

        assertThrows(HighwayCinemaException.class, () -> singleRoomCinema.addRoom(new Room(ROOM_NAME_ONE, STANDARD_CLEANING_SLOT)));
    }

    @Test
    public void whenTryingToScheduleTwoMoviesOnSameTimeShouldBeScheduledToTwoRooms() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE, false));

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));

        var roomOneSeanses = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(roomOneSeanses);
        Assert.assertEquals(1, roomOneSeanses.size());
        var roomOneScheduledSeans = roomOneSeanses.get(0);
        Assert.assertEquals(smurfsMovie, roomOneScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, roomOneScheduledSeans.getDate());

        var roomTwoSeanses = sut.listSeanses(ROOM_NAME_TWO);
        Assert.assertNotNull(roomTwoSeanses);
        Assert.assertEquals(1, roomTwoSeanses.size());
        var roomTwoScheduledSeans = roomTwoSeanses.get(0);
        Assert.assertEquals(avatarMovie, roomTwoScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, roomTwoScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleThreeMoviesOnSameTimeShouldScheduleItOnlyTwice() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE, false));

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));

        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE, false));

        var roomOneSeanses = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(roomOneSeanses);
        Assert.assertEquals(1, roomOneSeanses.size());
        var roomOneScheduledSeans = roomOneSeanses.get(0);
        Assert.assertEquals(smurfsMovie, roomOneScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, roomOneScheduledSeans.getDate());

        var roomTwoSeanses = sut.listSeanses(ROOM_NAME_TWO);
        Assert.assertNotNull(roomTwoSeanses);
        Assert.assertEquals(1, roomTwoSeanses.size());
        var roomTwoScheduledSeans = roomTwoSeanses.get(0);
        Assert.assertEquals(avatarMovie, roomTwoScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, roomTwoScheduledSeans.getDate());
    }
}
