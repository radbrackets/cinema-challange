package net.skarbek.cinemachallange.infrastructure;

import net.skarbek.cinemachallange.domain.model.Movie;
import net.skarbek.cinemachallange.domain.model.MovieId;

import java.util.Optional;

public interface MovieClient {

    Optional<Movie> getMovieById(MovieId movieId);
}
