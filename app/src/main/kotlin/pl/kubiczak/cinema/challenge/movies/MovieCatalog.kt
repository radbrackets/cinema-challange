package pl.kubiczak.cinema.challenge.movies

import arrow.core.Either
import pl.kubiczak.cinema.challenge.SomeError
import java.time.Duration

interface MovieCatalog {

    fun forId(id: Long): Either<SomeError, Movie>

    data class Movie(
        val id: MovieId,
        val duration: Duration,
        val title: String,
        val requirements: List<Requirement>
    ) {
        @JvmInline
        value class MovieId(val value: Long)
    }

    enum class Requirement {
        // open for discussion if this both should be here
        GLASSES_3D,
        PREMIERE
    }
}
