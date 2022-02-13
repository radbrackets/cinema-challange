package pl.kubiczak.cinema.challenge.dashboard.ports

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface IRetrieveDashboardElements {

    fun forDay(day: LocalDate): List<DashboardElement>

    data class DashboardElement(
        val startsAt: LocalTime,
        val duration: Duration,
        val cleaningDuration: Duration,
        val createdAt: LocalDateTime,
        val is3d: Boolean,
        val errors: List<ElementError>
    ) {
        enum class ElementError {
            OVERLAPS_ANOTHER_MOVIE,
            OVERLAPS_ROOM_UNAVAILABLE_SLOT
        }
    }
}
