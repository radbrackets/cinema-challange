package net.skarbek.cinemachallange.unit;

import net.skarbek.cinemachallange.configuration.CinemaSpecification;
import net.skarbek.cinemachallange.domain.model.CleaningOverhead;
import net.skarbek.cinemachallange.domain.model.Movie;
import net.skarbek.cinemachallange.domain.model.Seance;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;

import static net.skarbek.cinemachallange.builder.CinemaSpecificationBuilder.sampleCinemaSpecification;
import static net.skarbek.cinemachallange.builder.MovieBuilder.*;

public class SeanceInRoomTest {

    @Test
    public void shouldNotCreateSeansWhenItsStartsBeforeWorkingHours() {
        //given
        CinemaSpecification cinemaSpecification = sampleCinemaSpecification()
                .withOpenTime(LocalTime.of(8, 0))
                .build();
        Movie movie = sampleMovie().build();
        CleaningOverhead cleaningOverhead = new CleaningOverhead(Duration.ofMinutes(10));
        LocalTime startTime = LocalTime.of(7, 0);

        //when
        Throwable throwable = Assertions.catchThrowable(() ->
                new Seance(movie, cleaningOverhead, startTime, cinemaSpecification)

        );

        //then
        Assertions.assertThat(throwable)
                .isNotNull()
                .hasMessage("Seance has to starts in working hours");
    }

    @Test
    public void shouldNotCreateSeansWhenItsEndsAfterWorkingHours() {
        //given
        CinemaSpecification cinemaSpecification = sampleCinemaSpecification()
                .withCloseTime(LocalTime.of(22,0))
                .build();
        Movie movie = sampleMovie()
                .withDuration(Duration.ofMinutes(120))
                .build();
        CleaningOverhead cleaningOverhead = new CleaningOverhead(Duration.ofMinutes(10));
        LocalTime startTime = LocalTime.of(21, 0);

        //when
        Throwable throwable = Assertions.catchThrowable(() ->
                new Seance(movie, cleaningOverhead, startTime, cinemaSpecification)

        );

        //then
        Assertions.assertThat(throwable)
                .isNotNull()
                .hasMessage("Seance has to end in working hours");
    }
}
