package pl.kubiczak.cinema.challenge.dashboard

import pl.kubiczak.cinema.challenge.dashboard.ports.IRetrieveDashboardElements
import pl.kubiczak.cinema.challenge.dashboard.ports.IRetrieveDashboardElements.DashboardElement
import pl.kubiczak.cinema.challenge.dashboard.ports.IRetrieveDashboardElements.DashboardElement.DashboardElementError
import pl.kubiczak.cinema.challenge.movies.MovieCatalog
import pl.kubiczak.cinema.challenge.movies.MovieCatalog.Movie
import pl.kubiczak.cinema.challenge.movies.MovieCatalog.Requirement.GLASSES_3D
import pl.kubiczak.cinema.challenge.rooms.catalog.RoomsCatalog
import pl.kubiczak.cinema.challenge.rooms.unavailable.ports.IRetrieveReservations
import pl.kubiczak.cinema.challenge.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.Screening
import java.time.Duration
import java.time.LocalDate

// come of the dependencies are not used now
// they are here to show the idea of the dashboard computing the data required for view
class RetrieveDashboardElements(
    private val retrieveScreenings: IRetrieveScreenings,
    private val retrieveReservations: IRetrieveReservations,
    private val movieCatalog: MovieCatalog,
    private val roomsCatalog: RoomsCatalog
) : IRetrieveDashboardElements {

    override fun forDay(day: LocalDate): List<DashboardElement> {
        val screeningsSorted = retrieveScreenings.forDay(day).sortedBy { it.startsAt }
        val moviesById = mutableMapOf<Long, Movie>()
        screeningsSorted.map { screening ->
            // TODO: optimize so that movies are retrieved all at once
            movieCatalog.forId(screening.movieId).map {
                moviesById.put(it.id, it)
            }
        }
        return screeningsSorted.map { screening ->
            val movie = moviesById[screening.movieId]!!

            DashboardElement(
                startsAt = screening.startsAt,
                duration = movie.duration,
                // TODO: add room cleaning duration
                cleaningDuration = Duration.ZERO,
                is3d = movie.requirements.contains(GLASSES_3D),
                errors = checkForErrors(screening, screeningsSorted, moviesById)
            )
        }
    }

    private fun checkForErrors(
        screening: Screening,
        screeningsSorted: List<Screening>,
        moviesById: MutableMap<Long, Movie>,
    ): List<DashboardElementError> {
        val errors = mutableSetOf<DashboardElementError>()
        val thisStart = screening.startsAt
        val thisMovie = moviesById[screening.movieId]!!
        val thisEnd = screening.startsAt.plus(thisMovie.duration)
        // for every movie check if it starts after the previous one
        // TODO: what about the movies from previous day?
        screeningsSorted.forEach {
            val otherStart = it.startsAt
            val otherMovie = moviesById[it.movieId]!!
            val otherEnd = otherStart.plus(otherMovie.duration)
            // TODO: add check for overlapping cleaning time
            if (thisStart.isBefore(otherEnd) || thisEnd.isAfter(otherStart)) {
                errors.add(DashboardElementError.OVERLAPS_ANOTHER_MOVIE)
            }
        }
        return errors.toList()
    }
}
