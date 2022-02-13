package pl.kubiczak.cinema.challenge.screenings

import pl.kubiczak.cinema.challenge.screenings.ports.Screening

import java.time.LocalDate
import java.time.LocalTime

class TestScreeningBuilder {

    public static final LocalDate DEFAULT_DAY = LocalDate.parse('2022-01-01')

    private LocalDate day = DEFAULT_DAY
    private LocalTime startsAt = LocalTime.parse('12:00')
    private Long movieId = 123L
    private String roomId = 'Room 01'

    def withDay(LocalDate day) {
        this.day = day
        this
    }

    def withStartsAt(LocalTime startsAt) {
        this.startsAt = startsAt
        this
    }

    def withStartsAt(String startsAt) {
        this.startsAt = LocalTime.parse(startsAt)
        this
    }

    def withMovieId(Long movieId) {
        this.movieId = movieId
        this
    }

    def build() {
        new Screening(
                day,
                startsAt,
                movieId,
                roomId
        )
    }
}
