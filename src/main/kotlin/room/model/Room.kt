package room.model

import java.time.Duration
import java.time.LocalDateTime

data class Room(
    val roomId: Long,
    val name: String,
    val cleaningDuration: Duration,
) {
//    fun isRoomAvailableFor(date: LocalDateTime, duration: Duration): Boolean {
//        if (roomUnavailability.isNullOrEmpty()) return true
//        val endDate = date.plus(duration)
//        val filteredUnavailability = roomUnavailability!!.filter {
//            roomUnavailability -> roomUnavailability.isInUnavailabilityPeriod(date, endDate)
//        }
//        return filteredUnavailability.isEmpty()
//    }
}