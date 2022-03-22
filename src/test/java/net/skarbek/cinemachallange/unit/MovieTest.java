package net.skarbek.cinemachallange.unit;

import net.skarbek.cinemachallange.domain.model.Movie;
import net.skarbek.cinemachallange.domain.model.MovieId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static net.skarbek.cinemachallange.SampleData.MOVIE_ID;
import static net.skarbek.cinemachallange.SampleData.SHREK_NAME;

public class MovieTest {

    @ParameterizedTest(name = "{index}, {0} : {1} : {2} -> {3}")
    @MethodSource("illegalMovieData")
    public void shouldNotCreateMovieWithIllegalData(MovieId id, String name, Duration duration, String errorMessage) {

        //when
        Throwable exception = Assertions.catchThrowable(() ->
                new Movie(id, name, duration));
        //Then
        Assertions.assertThat(exception)
                .isNotNull()
                .hasMessage(errorMessage);
    }

    private static Stream<Arguments> illegalMovieData() {
        return Stream.of(
                Arguments.of(null, SHREK_NAME, Duration.ofMinutes(120), "MovieId cannot be null"),
                Arguments.of(MOVIE_ID, null, Duration.ofMinutes(120), "Movie name cannot be null"),
                Arguments.of(MOVIE_ID, SHREK_NAME, null, "duration cannot be null"),
                Arguments.of(MOVIE_ID, SHREK_NAME, Duration.ofMinutes(0), "duration cannot be 0"),
                Arguments.of(MOVIE_ID, SHREK_NAME, Duration.ofMinutes(-1), "duration cannot be negative")
        );
    }
}
