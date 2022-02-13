package pl.kubiczak.cinema.challenge.rooms.unavailable.ports

import pl.kubiczak.cinema.challenge.rooms.RoomId
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

interface IRetrieveReservations {

    fun forDay(day: LocalDate): Map<RoomId, List<Reservation>>

    /**
     * Describes time-slot when a room is unavailable for some reason.
     */
    data class Reservation(
        val startsAt: LocalDateTime,
        val duration: Duration
    )
}
