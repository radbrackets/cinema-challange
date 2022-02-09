package pl.kubiczak.cinema.challenge.planner


import pl.kubiczak.cinema.challenge.movies.catalog.MovieCatalog
import pl.kubiczak.cinema.challenge.movies.catalog.MovieFixture
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
        movieCatalog.forId(_) >> new MovieFixture(120, GLASSES_3D)

        when:
        def actual = movieCatalog.forId(123)

        then:
        actual.duration() == Duration.ofHours(2)
    }
}
