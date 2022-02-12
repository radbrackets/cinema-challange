package pl.kubiczak.cinema.challenge.jadwiga.screenings

import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.IManageScreenings
import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.IStoreScreenings
import spock.lang.Specification

import java.time.LocalDate

class ManageScreeningsTest extends Specification {

    private IRetrieveScreenings retrieveScreeningsMock

    private IStoreScreenings storeScreeningsMock

    private IManageScreenings tested

    def setup() {
        retrieveScreeningsMock = Mock()
        storeScreeningsMock = Mock()
        tested = new ManageScreenings(retrieveScreeningsMock, storeScreeningsMock)
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

    // testing for interactions
    def "should store added screening"() {
        given:
        def today = LocalDate.now()
        def screening = new TestScreeningBuilder()
                .withDay(today)
                .build()

        when:
        tested.create(screening)

        then:
        1 * storeScreeningsMock.create(screening)
    }
}
