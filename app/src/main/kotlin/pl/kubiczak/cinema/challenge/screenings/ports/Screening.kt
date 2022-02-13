package pl.kubiczak.cinema.challenge.screenings.ports

import pl.kubiczak.cinema.challenge.rooms.RoomId
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Screening(
    val day: LocalDate,
    val startsAt: LocalTime,
    val movieId: Long,

    val roomId: RoomId
)
