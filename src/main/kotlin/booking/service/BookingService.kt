package booking.service

import booking.model.Booking

class BookingService(private val bookingRepository: BookingRepository) {

    internal fun isAvailableFor(newBooking: Booking, bookings: List<Booking>): Boolean {
        if (bookings.isEmpty()) return true
        val filteredUnavailability = bookings.filter {
                roomUnavailability -> roomUnavailability.isInBookingPeriod(newBooking.bookingDateTime, newBooking.bookingDateTime.plus(newBooking.bookingDuration))
        }
        return filteredUnavailability.isEmpty()
    }

    fun findAllBookings(): List<Booking> = bookingRepository.findAll()

    fun findBookingByRoom(roomId: Long): List<Booking> = bookingRepository.findAll().filter { it.roomId == roomId }

    fun findBookingById(bookingId: Long) = bookingRepository.findAll().find { it.bookingId == bookingId }

    fun saveBooking(booking: Booking): Booking {
        val possiblyCollidingBookings = findBookingByRoom(booking.roomId)
        val isAvailableForBooking = isAvailableFor(booking, possiblyCollidingBookings)
        if (isAvailableForBooking) {
            return bookingRepository.save(booking)
        } else throw BookingTimeException(booking.roomId)
    }

    fun updateBooking(bookingId: Long, booking: Booking) {
        // we don't want to include our booking to update in our collision list, don't we?
        val possiblyCollidingBookings = findBookingByRoom(booking.roomId).filter { it.bookingId != bookingId }
        val isAvailableForBooking = isAvailableFor(booking, possiblyCollidingBookings)
        if (isAvailableForBooking) {
            bookingRepository.update(bookingId, booking)
        } else throw BookingTimeException(booking.roomId)
    }

    fun deleteBooking(bookingId: Long) = bookingRepository.delete(bookingId)

}

class BookingRepository {

    private var bookingList: List<Booking> = mutableListOf()

    fun findAll(): List<Booking> {
        return bookingList
    }

    fun save(booking: Booking): Booking {
        val existingBooking = bookingList.find { it.bookingId == booking.bookingId }
        if (existingBooking == null) bookingList = bookingList.plus(booking)
            else throw BookingAlreadyExistsException(booking.bookingId)
        return booking
    }

    fun update(bookingId: Long, booking: Booking): Booking {
        bookingList.find { it.bookingId == bookingId } ?: throw BookingNotFoundException(bookingId)
        if (bookingId != booking.bookingId) throw BookingUpdateException(bookingId, booking.bookingId)
        delete(bookingId)
        bookingList = bookingList.plus(booking)
        return booking
    }

    fun delete(bookingId: Long) {
        bookingList = bookingList.filter { it.bookingId != bookingId }
    }

    // what doesn't kill You makes You... stranger
    internal fun deleteAll() {
        bookingList = mutableListOf()
    }

}

class BookingTimeException(roomId: Long): Exception("Booking is colliding with another for room $roomId")
class BookingAlreadyExistsException(bookingId: Long) : Exception("Booking with id [$bookingId] already exists!")
class BookingUpdateException(bookingId: Long, updatedBookingId: Long) :
    Exception("Booking id mismatch: booking to update has id = [$bookingId] and updated booking data has id = [$updatedBookingId]")
class BookingNotFoundException(bookingId: Long) : Exception("Booking with id [$bookingId] not found!")