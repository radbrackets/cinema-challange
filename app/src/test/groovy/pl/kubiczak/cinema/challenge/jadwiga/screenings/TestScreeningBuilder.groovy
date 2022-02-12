package pl.kubiczak.cinema.challenge.jadwiga.screenings

import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.Screening

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TestScreeningBuilder {

    private LocalDate day = LocalDate.parse('2022-01-01')
    private LocalTime startsAt = LocalTime.parse('12:00')
    private Duration duration = Duration.ofMinutes(90)
    private Long movieId = 123L
    private String roomId = 'Room 01'
    private LocalDateTime createdAt = LocalDateTime.now()

    def withDay(LocalDate day) {
        this.day = day
        return this
    }

    def build() {
        new Screening(
                day,
                startsAt,
                movieId,
                roomId,
                createdAt
        )
    }
}
