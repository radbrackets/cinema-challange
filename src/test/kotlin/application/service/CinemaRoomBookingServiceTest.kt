package application.service

import booking.model.Booking
import booking.service.BookingRepository
import booking.service.BookingService
import booking.service.BookingTimeException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import movie.service.MovieRepository
import movie.service.MovieService
import room.service.RoomRepository
import room.service.RoomService
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month

class CinemaRoomBookingServiceTest : FunSpec({
    val cinemaRoomBookingService = CinemaRoomBookingService(
        bookingService = BookingService(BookingRepository()),
        roomService = RoomService(RoomRepository()),
        movieService = MovieService(MovieRepository()),
    )
    context("working hours check") {
        val baseBooking = Booking(
            bookingId = 21,
            roomId = 1,
            movieId = 1,
            bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0, 0),
            bookingDuration = Duration.ofHours(1)
        )
        test("given booking1 should be between working hours") {
            val isBetweenWorkingHours = cinemaRoomBookingService.isBookingBetweenWorkingHours(baseBooking)
            isBetweenWorkingHours shouldBe true
        }

        test("given booking2 should not be between working hours") {
            val booking2 = baseBooking.copy(
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 22, 0, 0)
            )
            val isBetweenWorkingHours = cinemaRoomBookingService.isBookingBetweenWorkingHours(booking2)
            isBetweenWorkingHours shouldBe false
        }

        test("given booking3 should be between working hours") {
            val booking3 = baseBooking.copy(
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 8, 0, 0),
                bookingDuration = Duration.ofHours(8)
            )
            val isBetweenWorkingHours = cinemaRoomBookingService.isBookingBetweenWorkingHours(booking3)
            isBetweenWorkingHours shouldBe true
        }

        test("given booking4 should be between working hours") {
            val booking4 = baseBooking.copy(
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 8, 0, 0),
                bookingDuration = Duration.ofHours(1)
            )
            val isBetweenWorkingHours = cinemaRoomBookingService.isBookingBetweenWorkingHours(booking4)
            isBetweenWorkingHours shouldBe true
        }

        test("given booking5 should be between working hours") {
            val booking5 = baseBooking.copy(
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 15, 0, 0),
                bookingDuration = Duration.ofHours(1)
            )
            val isBetweenWorkingHours = cinemaRoomBookingService.isBookingBetweenWorkingHours(booking5)
            isBetweenWorkingHours shouldBe true
        }

        test("given booking6 should not be between working hours") {
            val booking6 = baseBooking.copy(
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 21, 30, 0),
                bookingDuration = Duration.ofHours(2)
            )
            val isBetweenWorkingHours = cinemaRoomBookingService.isBookingBetweenWorkingHours(booking6)
            isBetweenWorkingHours shouldBe false
        }

        test("given booking7 should not be between working hours") {
            val booking6 = baseBooking.copy(
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 7, 0, 0),
                bookingDuration = Duration.ofHours(2)
            )
            val isBetweenWorkingHours = cinemaRoomBookingService.isBookingBetweenWorkingHours(booking6)
            isBetweenWorkingHours shouldBe false
        }
    }
    context("premiere time checks") {
        val baseBooking = Booking(
            bookingId = 37,
            roomId = 1,
            movieId = 1,
            bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 17, 30, 0),
            bookingDuration = Duration.ofHours(1)
        )

        test("given baseBooking should meet premiere hours condition") {
            val meetsThePremiereHoursCondition =
                cinemaRoomBookingService.isBookingStartTimeMeetingPremiereHoursCondition(baseBooking)
            meetsThePremiereHoursCondition shouldBe true
        }

        test("given booking1 should meet premiere hours condition") {
            val booking1 = baseBooking.copy(bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 17, 0, 0))
            val meetsThePremiereHoursCondition =
                cinemaRoomBookingService.isBookingStartTimeMeetingPremiereHoursCondition(booking1)
            meetsThePremiereHoursCondition shouldBe true
        }

        test("given booking2 should meet premiere hours condition") {
            val booking2 = baseBooking.copy(bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 17, 0, 0))
            val meetsThePremiereHoursCondition =
                cinemaRoomBookingService.isBookingStartTimeMeetingPremiereHoursCondition(booking2)
            meetsThePremiereHoursCondition shouldBe true
        }

        test("given booking3 should not meet premiere hours condition") {
            val booking3 = baseBooking.copy(bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 16, 59, 0))
            val meetsThePremiereHoursCondition =
                cinemaRoomBookingService.isBookingStartTimeMeetingPremiereHoursCondition(booking3)
            meetsThePremiereHoursCondition shouldBe false
        }

    }
    context("integration tests") {
        test("should book a room for specified movie") {
            val movieBooking = MovieBooking(
                bookingId = 1,
                movieId = 1,
                roomId = 1,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 0, 0)
            )
            cinemaRoomBookingService.bookRoomForAMovie(movieBooking)
            val bookings = cinemaRoomBookingService.findAllBookings()
            bookings.size shouldBe 1
        }
        test("should not book a room when booking is colliding with another booking") {
            val movieBooking = MovieBooking(
                bookingId = 2,
                movieId = 1,
                roomId = 1,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 10, 30, 0)
            )
            shouldThrow<BookingTimeException> { cinemaRoomBookingService.bookRoomForAMovie(movieBooking) }
            val bookings = cinemaRoomBookingService.findAllBookings()
            bookings.size shouldBe 1
        }
        test("should not book a room when premiere movie is booked before premiere time") {
            val movieBooking = MovieBooking(
                bookingId = 3,
                movieId = 2,
                roomId = 1,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 13, 30, 0)
            )
            shouldThrow<BookingNotMeetingPremiereHoursCondition> { cinemaRoomBookingService.bookRoomForAMovie(movieBooking) }
            val bookings = cinemaRoomBookingService.findAllBookings()
            bookings.size shouldBe 1
        }
        test("should not book a room when booking end time extends over cinema working hours") {
            val movieBooking = MovieBooking(
                bookingId = 4,
                movieId = 1,
                roomId = 1,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 21, 30, 0)
            )
            shouldThrow<BookingOutsideOfWorkingHoursException> { cinemaRoomBookingService.bookRoomForAMovie(movieBooking) }
            val bookings = cinemaRoomBookingService.findAllBookings()
            bookings.size shouldBe 1
        }
        test("should book a room when premiere movie is booked in premiere time") {
            val movieBooking = MovieBooking(
                bookingId = 5,
                movieId = 2,
                roomId = 1,
                bookingDateTime = LocalDateTime.of(2022, Month.JANUARY, 1, 17, 0)
            )
            cinemaRoomBookingService.bookRoomForAMovie(movieBooking)
            val bookings = cinemaRoomBookingService.findAllBookings()
            bookings.size shouldBe 2
        }
    }
})
