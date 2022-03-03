package booking.service

import booking.model.Booking
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month

class BookingServiceTest : FunSpec({
    val bookingService = BookingService(BookingRepository());
    val booking1 = Booking(
        bookingId = 1,
        roomId = 1,
        movieId = 1,
        bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0, 0),
        bookingDuration = Duration.ofHours(1)
    )
    val booking2 = Booking(
        bookingId = 2,
        roomId = 1,
        movieId = 2,
        bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 2, 11, 0, 0),
        bookingDuration = Duration.ofHours(1).plusMinutes(30)
    )
    val existingBookings = listOf(booking1, booking2)
    context("availability tests") {
        test("should show that given period is unavailable for the same period as the unavailability is") {
            val booking = Booking(
                bookingId = 3,
                roomId = 1,
                movieId = 3,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0, 0),
                bookingDuration = Duration.ofHours(1)
            )
            val isAvailable =
                bookingService.isAvailableFor(booking, existingBookings)
            isAvailable shouldBe false
        }
        test("should show that given period is available when requested date starts when unavailability ends") {
            val booking = Booking(
                bookingId = 4,
                roomId = 1,
                movieId = 3,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 11, 0, 0),
                bookingDuration = Duration.ofHours(1)
            )
            val isAvailable = bookingService.isAvailableFor(booking, existingBookings)
            isAvailable shouldBe true
        }
        test("should show that given period is available when requested date starts before unavailability starts and ends when unavailability starts") {
            val booking = Booking(
                bookingId = 5,
                roomId = 1,
                movieId = 3,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 9, 0, 0),
                bookingDuration = Duration.ofHours(1)
            )
            val isAvailable = bookingService.isAvailableFor(booking, existingBookings)
            isAvailable shouldBe true
        }
        test("should show that given period is unavailable when requested date starts after unavailability starts and ends before unavailability ends") {
            val booking = Booking(
                bookingId = 6,
                roomId = 1,
                movieId = 3,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 15, 0),
                bookingDuration = Duration.ofMinutes(30)
            )
            val isAvailable = bookingService.isAvailableFor(booking, existingBookings)
            isAvailable shouldBe false
        }
        test("should show that given period is unavailable when requested date starts after unavailability starts and ends when unavailability ends") {
            val booking = Booking(
                bookingId = 7,
                roomId = 1,
                movieId = 3,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 30, 0),
                bookingDuration = Duration.ofMinutes(30)
            )
            val isAvailable = bookingService.isAvailableFor(booking, existingBookings)
            isAvailable shouldBe false
        }
        test("should show that given period is unavailable when requested date starts before unavailability starts and ends after unavailability starts") {
            val booking = Booking(
                bookingId = 8,
                roomId = 1,
                movieId = 3,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 9, 0, 0),
                bookingDuration = Duration.ofHours(1).plusMinutes(30)
            )
            val isAvailable =
                bookingService.isAvailableFor(booking, existingBookings)
            isAvailable shouldBe false
        }
        test("should show that given period is unavailable when requested date starts before unavailability starts and ends after unavailability ends") {
            val booking = Booking(
                bookingId = 9,
                roomId = 1,
                movieId = 3,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 9, 30, 0, 0),
                bookingDuration = Duration.ofHours(2)
            )
            val isAvailable = bookingService.isAvailableFor(booking, existingBookings)
            isAvailable shouldBe false
        }
    }
    context("data tests") {
        test("should save bookings") {
            // when
            bookingService.saveBooking(booking1)
            bookingService.saveBooking(booking2)
            val fetchedUnavailabilities = bookingService.findBookingByRoom(1)
            // then
            fetchedUnavailabilities.size shouldBe 2
        }
        test("should delete given booking for id") {
            // when
            bookingService.deleteBooking(booking1.bookingId)
            val fetchedUnavailabilities = bookingService.findBookingByRoom(1)
            // then
            fetchedUnavailabilities.size shouldBe 1
        }
        test("should throw exception when new booking collides with existing booking") {
            // when
            val exception = shouldThrow<BookingTimeException> {
                bookingService.saveBooking(booking2)
            }
            // then
            exception.message shouldBe "Booking is colliding with another for room 1"
        }
    }
    context("updating tests") {
        val booking3 = Booking(
            bookingId = 3,
            roomId = 1,
            movieId = 2,
            bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 2, 10, 0, 0),
            bookingDuration = Duration.ofHours(1)
        )
        bookingService.saveBooking(booking3)
        test("should update booking") {
            val updatedBooking3 = booking3.copy(bookingDuration = Duration.ofMinutes(45))
            bookingService.updateBooking(3, updatedBooking3)
            val updated = bookingService.findBookingById(3)
            updated?.bookingDuration shouldBe Duration.ofMinutes(45)
        }
        test("should not update booking") {
            val updatedBooking3 = booking3.copy(bookingDuration = Duration.ofHours(1).plusMinutes(30))
            val exception = shouldThrow<BookingTimeException> {
                bookingService.updateBooking(3, updatedBooking3)
            }
            val notUpdated = bookingService.findBookingById(3)
            exception.message shouldBe "Booking is colliding with another for room 1"
            notUpdated?.bookingDuration shouldBe Duration.ofMinutes(45)
        }
    }

})
