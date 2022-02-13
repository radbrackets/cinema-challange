package pl.kubiczak.cinema.challenge.screenings

import arrow.core.Either
import pl.kubiczak.cinema.challenge.movies.MovieCatalog
import pl.kubiczak.cinema.challenge.movies.catalog.TestMovieBuilder
import pl.kubiczak.cinema.challenge.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.IStoreScreenings
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.LocalTime

import static pl.kubiczak.cinema.challenge.movies.MovieCatalog.Requirement.GLASSES_3D
import static pl.kubiczak.cinema.challenge.movies.MovieCatalog.Requirement.PREMIERE

class StoreScreeningsTest extends Specification {

    private IRetrieveScreenings retrieveScreeningsMock

    private IStoreScreenings storeScreeningsMock

    private MovieCatalog movieCatalogMock

    private IStoreScreenings tested

    def setup() {
        retrieveScreeningsMock = Mock()
        storeScreeningsMock = Mock()
        movieCatalogMock = Mock()
        tested = new ManageScreenings(retrieveScreeningsMock, storeScreeningsMock, movieCatalogMock)

        // by default catalog returns some movie
        def movie = new TestMovieBuilder().build()
        movieCatalogMock.forId(_) >> new Either.Right(movie)
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

    @Unroll
    def "should throw exception for screening time: #startTime of movie with requirements: #requirements"() {
        given:
        def movieId = 123
        def movie = new TestMovieBuilder().withId(movieId).withRequirements(requirements).build()

        and:
        def screening = new TestScreeningBuilder().withMovieId(movieId).withStartsAt(startTime).build()

        when:
        tested.create(screening)

        then:
        RuntimeException exception = thrown()
        exception
        and:
        1 * movieCatalogMock.forId(movieId) >> new Either.Right(movie)

        where:
        startTime                | requirements
        LocalTime.parse("07:59") | []
        LocalTime.parse("22:01") | []
        LocalTime.parse("07:59") | [GLASSES_3D, PREMIERE]
        LocalTime.parse("16:59") | [PREMIERE]
        LocalTime.parse("21:01") | [PREMIERE]
    }
}
