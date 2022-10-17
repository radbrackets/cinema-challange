package org.highwaycinemaplanner;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.mock;

public class TestSuiteEmptyCinema {
    HighWayCinemaPlanner sut;
    IMovieCatalog movieMock;

    private final LocalDateTime SEANS_DATE_ONE = LocalDateTime.of(2022, Month.NOVEMBER, 20, 15, 0);


    @Before
    public void before() {

        var cinema = new Cinema();

        movieMock = mock(IMovieCatalog.class);

        sut = new HighWayCinemaPlanner(cinema, movieMock);
    }

    @Test
    public void whenNoRoomsAreInCinemaListSeansesShouldReturnNull() {
        Assert.assertNull(sut.listSeanses("1"));
    }

    @Test
    public void whenSchedulingMovieOnNoRoomsCinemaShouldReturnNoRoomsAvailableStatus() {
        String AVATAR_MOVIE_NAME = "Avatar";
        Assert.assertEquals(ScheduleMovieStatus.NO_ROOMS_IN_CINEMA, sut.scheduleMovie(AVATAR_MOVIE_NAME, SEANS_DATE_ONE, false));
    }
}
