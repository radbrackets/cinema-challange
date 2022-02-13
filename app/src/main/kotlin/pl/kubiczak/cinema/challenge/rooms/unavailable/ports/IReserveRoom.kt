package pl.kubiczak.cinema.challenge.rooms.unavailable.ports

import pl.kubiczak.cinema.challenge.rooms.RoomId
import java.time.Duration
import java.time.LocalDateTime

interface IReserveRoom {

    fun reserve(roomId: RoomId, startsAt: LocalDateTime, duration: Duration)
}
