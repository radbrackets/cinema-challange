package pl.kubiczak.cinema.challenge.screenings

import pl.kubiczak.cinema.challenge.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.IStoreScreenings
import spock.lang.Specification

import java.time.LocalDate

class StoreScreeningsTest extends Specification {

    private IRetrieveScreenings retrieveScreeningsMock

    private IStoreScreenings storeScreeningsMock

    private IStoreScreenings tested

    def setup() {
        retrieveScreeningsMock = Mock()
        storeScreeningsMock = Mock()
        tested = new ManageScreenings(retrieveScreeningsMock, storeScreeningsMock)
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
