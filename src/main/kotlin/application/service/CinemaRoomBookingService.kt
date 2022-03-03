package application.service

import booking.model.Booking
import booking.service.BookingService
import movie.service.MovieService
import room.service.RoomService
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime

class CinemaRoomBookingService(
    private val bookingService: BookingService,
    private val movieService: MovieService,
    private val roomService: RoomService
) {
    companion object {
        // I could just create 2 separate vals... but I won't.
        val cinemaWorkingHours = Pair(LocalTime.of(8, 0), LocalTime.of(22, 0))
        val premiereStartingHour: LocalTime = LocalTime.of(17, 0)
    }

    fun bookRoomForAMovie(movieBooking: MovieBooking) {
        val booking = movieBooking.toBooking(
            movieDuration = movieService.findMovie(movieBooking.movieId).duration,
            cleaningDuration = roomService.findRoom(movieBooking.roomId).cleaningDuration
        )
        if (!isBookingBetweenWorkingHours(booking)) throw BookingOutsideOfWorkingHoursException()
        // I am battling against nested ifs on reviews, and here I am, ending up creating one...
        if (movieService.findMovie(movieBooking.movieId).isPremiere) isBookingStartTimeMeetingPremiereHoursCondition(
            booking
        ).also { meetsCondition ->
            if (!meetsCondition) throw BookingNotMeetingPremiereHoursCondition()
        }
        bookingService.saveBooking(booking)
    }

    internal fun findAllBookings(): List<Booking> = bookingService.findAllBookings()

    internal fun isBookingBetweenWorkingHours(booking: Booking): Boolean {
        val bookingEndingDateTime = booking.bookingDateTime.plus(booking.bookingDuration)
        val bookingStartHour = LocalTime.of(booking.bookingDateTime.hour, booking.bookingDateTime.minute)
        val bookingEndHour = LocalTime.of(bookingEndingDateTime.hour, bookingEndingDateTime.minute)
        return (bookingStartHour.isAfter(cinemaWorkingHours.first) || bookingStartHour.equals(cinemaWorkingHours.first))
                && (bookingEndHour.isBefore(cinemaWorkingHours.second) || bookingEndHour.equals(cinemaWorkingHours.second))
                && (!bookingStartHour.isBefore(cinemaWorkingHours.first) || !bookingEndHour.isAfter(cinemaWorkingHours.second))
    }

    // checkIfDateBetweenWorkingHours(booking: Booking) will always run before running this, so we can
    // boldly assume that we do not need to repeat checks
    internal fun isBookingStartTimeMeetingPremiereHoursCondition(booking: Booking): Boolean {
        val bookingStartHour = LocalTime.of(booking.bookingDateTime.hour, booking.bookingDateTime.minute)
        return premiereStartingHour == bookingStartHour || premiereStartingHour.isBefore(bookingStartHour)
    }
}

class BookingOutsideOfWorkingHoursException: Exception("Booking is outside cinema working hours!")
class BookingNotMeetingPremiereHoursCondition: Exception("Booking doesn't meet premiere hours condition!")

data class MovieBooking(
    val bookingId: Long,
    val roomId: Long,
    val movieId: Long,
    val bookingDateTime: LocalDateTime,
)

fun MovieBooking.toBooking(movieDuration: Duration, cleaningDuration: Duration): Booking {
    return Booking(
        bookingId = this.bookingId,
        roomId = this.roomId,
        movieId = this.movieId,
        bookingDateTime = this.bookingDateTime,
        bookingDuration = movieDuration.plus(cleaningDuration)
    )
}