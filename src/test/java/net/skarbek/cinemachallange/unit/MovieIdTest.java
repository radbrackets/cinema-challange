package net.skarbek.cinemachallange.unit;


import net.skarbek.cinemachallange.domain.model.MovieId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;

public class MovieIdTest {

    @Test
    public void shouldNotCreateNull() {
        //when
        Throwable exception = catchThrowable((() -> new MovieId(null)));
        //then
        Assertions.assertThat(exception)
                .isNotNull()
                .hasMessage("MovieId cannot be null");
    }
}
