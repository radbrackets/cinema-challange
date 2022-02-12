package pl.kubiczak.cinema.challenge.jadwiga.screenings.ports

import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

data class Screening(
    val day: LocalDate,
    val startsAt: LocalTime,
    val duration: Duration,
    val movieId: Long,
    val comment: String?
)
