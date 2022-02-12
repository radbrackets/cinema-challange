package pl.kubiczak.cinema.challenge.rooms

import arrow.core.Either
import pl.kubiczak.cinema.challenge.SomeError
import java.time.Duration

interface RoomsCatalog {

    fun findById(id: String): Either<SomeError, Room>

    data class Room(
        val id: String,
        val cleaningDuration: Duration
    )
}
