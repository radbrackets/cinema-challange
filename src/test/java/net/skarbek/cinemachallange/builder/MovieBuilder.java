package net.skarbek.cinemachallange.builder;

import net.skarbek.cinemachallange.SampleData;
import net.skarbek.cinemachallange.domain.model.Movie;
import net.skarbek.cinemachallange.domain.model.MovieId;
import net.skarbek.cinemachallange.infrastructure.fake.FakeMovieClient;

import java.time.Duration;

public class MovieBuilder {

    private final FakeMovieClient fakeMovieClient;
    private MovieId id = SampleData.MOVIE_ID;
    private String name = SampleData.SHREK_NAME;
    private Duration duration = Duration.ofMinutes(10);

    public MovieBuilder(FakeMovieClient fakeMovieClient) {
        this.fakeMovieClient = fakeMovieClient;
    }

    public static MovieBuilder sampleMovie() {
        return new MovieBuilder(null);
    }

    public static MovieBuilder sampleMovie(FakeMovieClient fakeMovieClient){
        return new MovieBuilder(fakeMovieClient);
    }

    public MovieBuilder withId(MovieId id) {
        this.id = id;
        return this;
    }

    public MovieBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MovieBuilder withDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public Movie build() {
        return new Movie(id, name, duration);
    }

    public void inExternalSource() {
        fakeMovieClient.addMovie(this.build());
    }
}
