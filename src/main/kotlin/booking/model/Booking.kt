package booking.model

import application.service.MovieBooking
import java.time.Duration
import java.time.LocalDateTime

data class Booking(
    val bookingId: Long,
    val roomId: Long,
    val movieId: Long?,
    val bookingDateTime: LocalDateTime,
    val bookingDuration: Duration
) {
    fun isInBookingPeriod(startTime: LocalDateTime, endTime: LocalDateTime): Boolean {
        // you won't even imagine how much time have I spend on comparing dates in this application.
        val startTimeComparedToBookingStartTime = startTime.compareTo(bookingDateTime)
        val bookingEndTime = bookingDateTime.plus(bookingDuration)
        val startTimeComparedToBookingEndTime = startTime.compareTo(bookingEndTime)
        val endTimeComparedToBookingStartTime = endTime.compareTo(bookingDateTime)
        return (startTimeComparedToBookingStartTime == 0
                || (startTimeComparedToBookingStartTime > 0 && startTimeComparedToBookingEndTime < 0)
                || (startTimeComparedToBookingStartTime < 0 && endTimeComparedToBookingStartTime > 0))
    }

    fun MovieBooking.toBooking(movieDuration: Duration, cleaningDuration: Duration): Booking {
        return Booking(
            bookingId = this.bookingId,
            roomId = this.roomId,
            movieId = this.movieId,
            bookingDateTime = this.bookingDateTime,
            bookingDuration = movieDuration.plus(cleaningDuration)
        )
    }
}
