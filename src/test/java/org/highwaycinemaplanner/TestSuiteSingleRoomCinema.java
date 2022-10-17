package org.highwaycinemaplanner;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class TestSuiteSingleRoomCinema {
    HighWayCinemaPlanner sut;
    IMovieCatalog movieMock;

    private final String ROOM_NAME_ONE = "1";
    private final String AVATAR_MOVIE_NAME = "Avatar";
    private final Movie avatarMovie = new Movie(AVATAR_MOVIE_NAME, 150);

    private  final String SMURFS_MOVIE_NAME = "Smurfs";
    private final Movie smurfsMovie = new Movie(SMURFS_MOVIE_NAME, 30);

    private final String NOT_EXISTING_MOVIE = "Not existing movie";

    private final LocalDateTime SEANS_DATE_ONE = LocalDateTime.of(2022, Month.NOVEMBER, 20, 15, 0);
    private final LocalDateTime SEANS_DATE_TWO = LocalDateTime.of(2022, Month.NOVEMBER, 20, 19, 0);


    @Before
    public void before() {
        var singleRoomCinema = new Cinema();

        assertDoesNotThrow(() -> singleRoomCinema.addRoom(new Room(ROOM_NAME_ONE, 15)));

        movieMock = mock(IMovieCatalog.class);

        sut = new HighWayCinemaPlanner(singleRoomCinema, movieMock);

        when(movieMock.getMovie(NOT_EXISTING_MOVIE)).thenReturn(null);
        when(movieMock.getMovie(AVATAR_MOVIE_NAME)).thenReturn(avatarMovie);
        when(movieMock.getMovie(SMURFS_MOVIE_NAME)).thenReturn(smurfsMovie);
    }

    @Test
    public void whenNothingWasScheduledListSeansesShouldReturnEmptyList() {
        var seanses = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seanses);
        Assert.assertTrue(seanses.isEmpty());
    }

    @Test
    public void whenTryingToScheduleMovieThatIsNotPresentInMovieCatalogShouldReturnMovieNotFoundInDictionaryStatusAndNotScheduleIt() {
        Assert.assertEquals(sut.scheduleMovie(NOT_EXISTING_MOVIE, SEANS_DATE_ONE, false), ScheduleMovieStatus.MOVIE_NOT_FOUND_IN_CATALOG);

        verify(movieMock, times(1)).getMovie(NOT_EXISTING_MOVIE);
        var seanses = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seanses);
        Assert.assertEquals(0, sut.listSeanses(ROOM_NAME_ONE).size());
    }

    @Test
    public void whenSchedulingProperMovieItShouldBeScheduled() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));

        verify(movieMock, times(1)).getMovie(AVATAR_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var scheduledSeans = seansesList.get(0);
        Assert.assertEquals(1, seansesList.size());
        Assert.assertEquals(avatarMovie, scheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, scheduledSeans.getDate());
    }

    @Test
    public void whenSchedulingProperMovieTwiceOnSameDateItShouldBeScheduledOnlyOnce() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));

        verify(movieMock, times(2)).getMovie(AVATAR_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var scheduledSeans = seansesList.get(0);
        Assert.assertEquals(1, seansesList.size());
        Assert.assertEquals(avatarMovie, scheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, scheduledSeans.getDate());
    }

    @Test
    public void whenSchedulingTwoProperMovieTheyShouldBeScheduled() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_TWO, false));

        verify(movieMock, times(2)).getMovie(AVATAR_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var firstScheduledSeans = seansesList.get(0);
        var secondScheduledSeans = seansesList.get(0);

        Assert.assertEquals(2, seansesList.size());

        Assert.assertEquals(avatarMovie, firstScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, firstScheduledSeans.getDate());

        Assert.assertEquals(avatarMovie, secondScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, secondScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleMovieWithItsEndOverlappingWithPreviousShouldNotScheduleIt() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME,
                sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE.minusMinutes(avatarMovie.getLengthInMinutes() - 1), false));

        verify(movieMock, times(2)).getMovie(AVATAR_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var firstScheduledSeans = seansesList.get(0);

        Assert.assertEquals(1, seansesList.size());

        Assert.assertEquals(avatarMovie, firstScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, firstScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleMovieWithItsStartOverlappingWithPreviousShouldNotScheduleIt() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME,
                sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE.plusMinutes(avatarMovie.getLengthInMinutes() - 1), false));

        verify(movieMock, times(2)).getMovie(AVATAR_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var firstScheduledSeans = seansesList.get(0);

        Assert.assertEquals(1, seansesList.size());

        Assert.assertEquals(avatarMovie, firstScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, firstScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleMovieThatIsInsideOtherMoviePlayTimeShouldNotScheduleIt() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE.plusMinutes(30), false));

        verify(movieMock, times(1)).getMovie(AVATAR_MOVIE_NAME);
        verify(movieMock, times(1)).getMovie(SMURFS_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var firstScheduledSeans = seansesList.get(0);

        Assert.assertEquals(1, seansesList.size());

        Assert.assertEquals(avatarMovie, firstScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, firstScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleMovieThatOverlapsAnotherMovieOnBothSidesOfPlayTimeShouldNotScheduleIt() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME,
                sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE.minusMinutes(30), false));

        verify(movieMock, times(1)).getMovie(AVATAR_MOVIE_NAME);
        verify(movieMock, times(1)).getMovie(SMURFS_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var firstScheduledSeans = seansesList.get(0);

        Assert.assertEquals(1, seansesList.size());

        Assert.assertEquals(smurfsMovie, firstScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, firstScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleAMovieThatItsCleaningSlotOverlapsWithAnotherMovieItShouldNotScheduleIt() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE.minusMinutes(smurfsMovie.getLengthInMinutes() + 10), false));

        verify(movieMock, times(2)).getMovie(SMURFS_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var firstScheduledSeans = seansesList.get(0);

        Assert.assertEquals(1, seansesList.size());

        Assert.assertEquals(smurfsMovie, firstScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, firstScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleAMovieOverlapsWithAnotherMovieCleaningSlotShouldNotScheduleIt() {
        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED, sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE, false));
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, SEANS_DATE_ONE.plusMinutes(smurfsMovie.getLengthInMinutes() + 10), false));

        verify(movieMock, times(2)).getMovie(SMURFS_MOVIE_NAME);

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);
        var firstScheduledSeans = seansesList.get(0);

        Assert.assertEquals(1, seansesList.size());

        Assert.assertEquals(smurfsMovie, firstScheduledSeans.getMovie());
        Assert.assertEquals(SEANS_DATE_ONE, firstScheduledSeans.getDate());
    }

    @Test
    public void whenTryingToScheduleAMovieBeforeCinemaOpenTimeShouldNotScheduleIt() {
        final LocalDateTime PRE_OPEN_TIME = LocalDateTime.of(2022, Month.NOVEMBER, 20, 6, 55);

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_OUTSIDE_OF_CINEMA_WORKING_HOURS,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, PRE_OPEN_TIME, false));

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);

        Assert.assertEquals(0, seansesList.size());
    }

    @Test
    public void whenTryingToScheduleAMovieAfterCinemaOpenTimeShouldNotScheduleIt() {
        final LocalDateTime JUST_BEFORE_CINEMA_CLOSE = LocalDateTime.of(2022, Month.NOVEMBER, 20, 22, 55);

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_OUTSIDE_OF_CINEMA_WORKING_HOURS,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, JUST_BEFORE_CINEMA_CLOSE, false));

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);

        Assert.assertEquals(0, seansesList.size());
    }

    @Test
    public void whenTryingToScheduleLongMovieAfterCinemaOpenTimeShouldNotScheduleIt() {
        final LocalDateTime JUST_BEFORE_CINEMA_CLOSE = LocalDateTime.of(2022, Month.NOVEMBER, 20, 22, 55);

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_OUTSIDE_OF_CINEMA_WORKING_HOURS,
                sut.scheduleMovie(AVATAR_MOVIE_NAME, JUST_BEFORE_CINEMA_CLOSE, false));

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);

        Assert.assertEquals(0, seansesList.size());
    }

    @Test
    public void whenTryingToScheduleAPremiereMovieBeforePremiereTimeShouldNotScheduleIt() {
        final LocalDateTime PRE_PREMIERE_TIME = LocalDateTime.of(2022, Month.NOVEMBER, 20, 16, 55);

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_OUTSIDE_OF_CINEMA_PREMIERE_HOURS,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, PRE_PREMIERE_TIME, true));

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);

        Assert.assertEquals(0, seansesList.size());
    }

    @Test
    public void whenTryingToScheduleAPremiereMovieAfterPremiereTimeShouldNotScheduleIt() {
        final LocalDateTime POST_PREMIERE_TIME = LocalDateTime.of(2022, Month.NOVEMBER, 20, 22, 0);

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_OUTSIDE_OF_CINEMA_PREMIERE_HOURS,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, POST_PREMIERE_TIME, true));

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);

        Assert.assertEquals(0, seansesList.size());
    }

    @Test
    public void whenTryingToScheduleAPremiereInProperTimeShouldScheduleIt() {
        final LocalDateTime TIME_MATCHING_PREMIERE_TIME = LocalDateTime.of(2022, Month.NOVEMBER, 20, 19, 0);

        Assert.assertEquals(ScheduleMovieStatus.MOVIE_SCHEDULED,
                sut.scheduleMovie(SMURFS_MOVIE_NAME, TIME_MATCHING_PREMIERE_TIME, true));

        var seansesList = sut.listSeanses(ROOM_NAME_ONE);
        Assert.assertNotNull(seansesList);

        Assert.assertEquals(1, seansesList.size());
    }

    @Test
    public void whenTryingToListSeansesOnIncorrectRoomNameItShouldReturnNull() {
        final String NOT_EXISTING_ROOM_NAME = "2";
        Assert.assertNull(sut.listSeanses(NOT_EXISTING_ROOM_NAME));
    }
}
