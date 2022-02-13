package pl.kubiczak.cinema.challenge.rooms.catalog

import arrow.core.Either
import pl.kubiczak.cinema.challenge.SomeError
import pl.kubiczak.cinema.challenge.rooms.RoomId
import java.time.Duration

interface RoomsCatalog {

    fun findById(id: String): Either<SomeError, Room>

    data class Room(
        val id: RoomId,
        val cleaningDuration: Duration
    )
}
