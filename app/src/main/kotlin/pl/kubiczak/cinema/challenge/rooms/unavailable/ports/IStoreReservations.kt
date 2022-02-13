package pl.kubiczak.cinema.challenge.rooms.unavailable.ports

import pl.kubiczak.cinema.challenge.rooms.RoomId
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

interface IStoreReservations {

    fun create(reservation: Reservation)

    fun update(id: UUID, reservation: Reservation)

    fun delete(id: UUID)

    data class Reservation(
        val id: UUID,
        val roomId: RoomId,
        val startsAt: LocalDateTime,
        val duration: Duration
    )
}
