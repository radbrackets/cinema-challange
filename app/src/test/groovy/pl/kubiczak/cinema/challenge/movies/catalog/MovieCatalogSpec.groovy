package pl.kubiczak.cinema.challenge.movies.catalog

import arrow.core.Either
import pl.kubiczak.cinema.challenge.movies.MovieCatalog
import spock.lang.Specification

import java.time.Duration

class MovieCatalogSpec extends Specification {

    private MovieCatalog movieCatalog

    def setup() {
        movieCatalog = Mock()
    }

    def "should find movie"() {
        given:
        def movie = new TestMovieBuilder().withDuration(120).build()
        and:
        movieCatalog.forId(_) >> new Either.Right(movie)

        when:
        def actual = movieCatalog.forId(123)

        then:
        actual.map {
            assert it.duration == Duration.ofHours(2)
        }
    }
}
