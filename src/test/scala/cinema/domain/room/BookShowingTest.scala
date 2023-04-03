package cinema.domain.room

import cinema.domain._
import cinema.domain.room.timeslot.Cleaning
import cinema.domain.room.timeslot.Showing

import scala.concurrent.duration.DurationInt

class BookShowingTest extends RoomTestSupport {

  "Room" should {
    "book showing correctly" when {
      "booked timeslots are empty" in {
        val showing = Showing(0, 0, 15 :: 30, 2.hours)

        val room = anyRoom.bookShowing(15 :: 30, anyMovie)

        room.value.bookedTimeslots should contain(showing)
      }

      "it fits into timeslot" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val showing = Showing(0, 0, 14 :: 10, 2.hours)

        val room = roomWithShowings.bookShowing(14 :: 10, anyMovie)

        room.value.bookedTimeslots should have length (preBookedSlots.length + 2)
        room.value.bookedTimeslots should contain(showing)
        room.value.bookedTimeslots should contain(Cleaning(16 :: 10, cleaningDuration))
      }
    }

    "add cleaning time after showing" in {
      val room = anyRoom.bookShowing(15 :: 30, anyMovie)

      room.value.bookedTimeslots should contain(Cleaning(17 :: 30, cleaningDuration))
    }

    "fail" when {
      "booking showing but timeslot is occupied" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val room = roomWithShowings.bookShowing(15 :: 10, anyMovie)

        assert(room.isLeft)
      }

      "there isn't enough time for cleaning" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val room = roomWithShowings.bookShowing(14 :: 30, anyMovie)

        assert(room.isLeft)
      }
    }
  }

}
