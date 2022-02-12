package pl.kubiczak.cinema.challenge.movies.catalog

import arrow.core.Either
import pl.kubiczak.cinema.challenge.SomeError

interface MovieCatalog {

    fun forId(id: Long): Either<SomeError, Movie>
}
