package cinema.domain.room

import cinema.domain.Minute
import cinema.domain.timeslot.Unavailable

import scala.concurrent.duration.DurationInt

class MarkAsUnavailableTests extends RoomTestSupport {

  "Room" should {
    "mark room as unavailable" when {
      "it fits into timeslot" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val unavailable = Unavailable(0, 14 :: 30, 2.hours)

        val room = roomWithShowings.markRoomAsUnavailable(unavailable)

        room.value.bookedTimeslots should have length (preBookedSlots.length + 1)
        room.value.bookedTimeslots should contain(unavailable)
      }
    }

    "fail" when {
      "marking room as unavailable but timeslot is occupied" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val unavailable = Unavailable(0, 12 :: 30, 8.hours)
        val room        = roomWithShowings.markRoomAsUnavailable(unavailable)

        assert(room.isLeft)
      }
    }
  }

}
