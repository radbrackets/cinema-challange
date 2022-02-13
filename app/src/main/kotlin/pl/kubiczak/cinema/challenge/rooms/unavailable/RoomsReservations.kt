package pl.kubiczak.cinema.challenge.rooms.unavailable

import pl.kubiczak.cinema.challenge.rooms.RoomId
import pl.kubiczak.cinema.challenge.rooms.unavailable.ports.IReserveRoom
import pl.kubiczak.cinema.challenge.rooms.unavailable.ports.IRetrieveReservations
import pl.kubiczak.cinema.challenge.rooms.unavailable.ports.IRetrieveReservations.Reservation
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

class RoomsReservations : IReserveRoom, IRetrieveReservations {

    override fun reserve(roomId: RoomId, startsAt: LocalDateTime, duration: Duration) {
        TODO("Not yet implemented")
    }

    override fun forDay(day: LocalDate): Map<RoomId, List<Reservation>> {
        TODO("Not yet implemented")
    }
}
