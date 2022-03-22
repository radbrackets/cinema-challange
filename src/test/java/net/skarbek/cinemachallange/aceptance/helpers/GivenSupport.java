package net.skarbek.cinemachallange.aceptance.helpers;

import net.skarbek.cinemachallange.builder.MovieBuilder;
import net.skarbek.cinemachallange.builder.RoomBuilder;
import net.skarbek.cinemachallange.configuration.CinemaSpecification;
import net.skarbek.cinemachallange.infrastructure.fake.FakeMovieClient;
import net.skarbek.cinemachallange.infrastructure.fake.FakeRoomClient;

public class GivenSupport {

    private final FakeMovieClient fakeMovieClient;
    private final FakeRoomClient fakeRoomClient;
    private final CinemaSpecification cinemaSpecification;

    public GivenSupport(FakeMovieClient fakeMovieClient, FakeRoomClient fakeRoomClient, CinemaSpecification cinemaSpecification) {
        this.fakeMovieClient = fakeMovieClient;
        this.fakeRoomClient = fakeRoomClient;
        this.cinemaSpecification = cinemaSpecification;
    }

    public MovieBuilder sampleMovie() {
        return MovieBuilder.sampleMovie(fakeMovieClient);
    }

    public RoomBuilder sampleRoom() {
        return RoomBuilder.sampleRoom(fakeRoomClient);
    }

    public CinemaSpecification cinemaSpecification() {
        return cinemaSpecification;
    }
}
