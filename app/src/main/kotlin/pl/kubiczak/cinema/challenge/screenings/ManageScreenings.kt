package pl.kubiczak.cinema.challenge.screenings

import pl.kubiczak.cinema.challenge.movies.MovieCatalog
import pl.kubiczak.cinema.challenge.movies.MovieCatalog.Requirement.PREMIERE
import pl.kubiczak.cinema.challenge.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.IStoreScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.Screening
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class ManageScreenings(
    // separation of retrieving and storing data especially for i.e. Kafka
    private val retrieveScreenings: IRetrieveScreenings,
    private val storeScreenings: IStoreScreenings,
    private val movieCatalog: MovieCatalog
) : IRetrieveScreenings, IStoreScreenings {

    companion object {
        // should rather be moved to properties or validation logic extracted to separate component
        val MIN_PREMIERE_START = LocalTime.parse("17:00")
        val MAX_PREMIERE_START = LocalTime.parse("21:00")
        val MIN_START = LocalTime.parse("08:00")
        val MAX_START = LocalTime.parse("22:00")
    }

    override fun forDay(day: LocalDate): List<Screening> =
        retrieveScreenings.forDay(day)

    override fun create(screening: Screening) {
        validateTime(screening)
        storeScreenings.create(screening)
    }

    override fun update(id: UUID, screening: Screening) {
        validateTime(screening)
        storeScreenings.update(id, screening)
    }

    override fun delete(id: UUID) {
        storeScreenings.delete(id)
    }

    // it might be discussed if:
    // - is it enough to throw exception for invalid time
    // - should the logic be extracted to validation component
    // - all validation done while presenting (as implemented for overlapping timeslots)
    private fun validateTime(screening: Screening) {
        val movie = movieCatalog.forId(screening.movieId)
        movie.map {
            if (it.requirements.contains(PREMIERE)) {
                require(!screening.startsAt.isBefore(MIN_PREMIERE_START))
                require(!screening.startsAt.isAfter(MAX_PREMIERE_START))
            } else {
                require(!screening.startsAt.isBefore(MIN_START))
                require(!screening.startsAt.isAfter(MAX_START))
            }
        }
    }
}
