package pl.kubiczak.cinema.challenge.movies.catalog

import arrow.core.Either
import spock.lang.Specification

import java.time.Duration

import static pl.kubiczak.cinema.challenge.movies.catalog.Movie.Requirement.GLASSES_3D

class SimpleSpec extends Specification {

    private MovieCatalog movieCatalog

    def setup() {
        movieCatalog = Mock()
    }

    def "should find movie"() {
        given:
        movieCatalog.forId(_) >> new Either.Right(new TestMovie(120, GLASSES_3D))

        when:
        def actual = movieCatalog.forId(123)

        then:
        actual.map {
            assert it.duration() == Duration.ofHours(2)
        }
    }
}
