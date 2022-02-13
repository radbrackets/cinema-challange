package pl.kubiczak.cinema.challenge.dashboard

import arrow.core.Either
import pl.kubiczak.cinema.challenge.movies.MovieCatalog
import pl.kubiczak.cinema.challenge.movies.catalog.TestMovieBuilder
import pl.kubiczak.cinema.challenge.screenings.TestScreeningBuilder
import pl.kubiczak.cinema.challenge.screenings.ports.IRetrieveScreenings
import spock.lang.Specification

import static pl.kubiczak.cinema.challenge.dashboard.ports.IRetrieveDashboardElements.DashboardElement.DashboardElementError.OVERLAPS_ANOTHER_MOVIE

class RetrieveDashboardElementsSpec extends Specification {

    private IRetrieveScreenings retrieveScreeningsMock
    private MovieCatalog movieCatalogMock

    private RetrieveDashboardElements tested

    def setup() {
        retrieveScreeningsMock = Mock()
        movieCatalogMock = Mock()
        tested = new RetrieveDashboardElements(
                retrieveScreeningsMock,
                movieCatalogMock
        )
    }

    def "should mark screening as overlapping"() {
        given:
        def movie = new TestMovieBuilder().withDuration(60).build()
        movieCatalogMock.forId(_) >> new Either.Right(movie)

        and:
        def aScreening = new TestScreeningBuilder()
                .withStartsAt("12:00").build()
        def anotherScreening = new TestScreeningBuilder()
                .withStartsAt("12:30").build()
        retrieveScreeningsMock.forDay(_) >> [
                aScreening, anotherScreening
        ]

        when:
        def actual = tested.forDay(TestScreeningBuilder.DEFAULT_DAY)

        then:
        actual.size() == 2
        actual.forEach {
            assert it.errors.contains(OVERLAPS_ANOTHER_MOVIE)
        }
    }

    // TODO: add more unit tests to cover all cases...
}
