package net.skarbek.cinemachallange.infrastructure.fake;

import net.skarbek.cinemachallange.domain.model.Movie;
import net.skarbek.cinemachallange.domain.model.MovieId;
import net.skarbek.cinemachallange.infrastructure.MovieClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//Temporary, move to tests package after implement real client
@Component
public class FakeMovieClient implements MovieClient, TestClearableSource{

    private static final Map<MovieId, Movie> MOVIES = new HashMap<>();


    @Override
    public Optional<Movie> getMovieById(MovieId movieId) {
        Movie movie = MOVIES.getOrDefault(movieId, null);
        return Optional.ofNullable(movie);
    }

    public void addMovie(Movie movie) {
        MOVIES.put(movie.getId(), movie);
    }

    @Override
    public void cleanUp() {
        MOVIES.clear();
    }
}
