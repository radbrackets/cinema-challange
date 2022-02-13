package pl.kubiczak.cinema.challenge.screenings

import pl.kubiczak.cinema.challenge.movies.MovieCatalog
import pl.kubiczak.cinema.challenge.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.IStoreScreenings
import spock.lang.Specification

import java.time.LocalDate

class RetrieveScreeningsTest extends Specification {

    private IRetrieveScreenings retrieveScreeningsMock

    private IStoreScreenings storeScreeningsMock

    private MovieCatalog movieCatalogMock

    private IRetrieveScreenings tested

    def setup() {
        retrieveScreeningsMock = Mock()
        storeScreeningsMock = Mock()
        movieCatalogMock = Mock()
        tested = new ManageScreenings(retrieveScreeningsMock, storeScreeningsMock, movieCatalogMock)
    }

    // testing for state
    def "should return the list of retrieved screenings"() {
        given:
        def screeningA = new TestScreeningBuilder().build()
        def screeningB = new TestScreeningBuilder().build()
        retrieveScreeningsMock.forDay(_) >> [
                screeningA,
                screeningB
        ]

        when:
        def actual = tested.forDay(LocalDate.parse('2022-01-01'))

        then:
        actual == [screeningA, screeningB]
    }
}
