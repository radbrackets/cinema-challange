package cinema.domain.room

import cinema.domain.Minute
import cinema.domain.timeslot.Cleaning
import cinema.domain.timeslot.Showing

import scala.concurrent.duration.DurationInt

class BookShowingTests extends RoomTestSupport {

  "Room" should {
    "book showing correctly" when {
      "booked timeslots are empty" in {
        val showing = Showing(0, 0, 15 :: 30, 1.hours)

        val room = anyRoom.bookShowing(showing)

        room.value.bookedTimeslots should contain(showing)
      }

      "it fits into timeslot" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val showing = Showing(0, 0, 14 :: 10, 1.hours)

        val room = roomWithShowings.bookShowing(showing)

        room.value.bookedTimeslots should have length (preBookedSlots.length + 2)
        room.value.bookedTimeslots should contain(showing)
        room.value.bookedTimeslots should contain(Cleaning(15 :: 10, cleaningDuration))
      }
    }

    "add cleaning time after showing" in {
      val showing = Showing(0, 0, 15 :: 30, 1.hours)

      val room = anyRoom.bookShowing(showing)

      room.value.bookedTimeslots should contain(Cleaning(16 :: 30, cleaningDuration))
    }

    "fail" when {
      "booking showing but timeslot is occupied" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val showing = Showing(0, 0, 15 :: 10, 2.hours)
        val room    = roomWithShowings.bookShowing(showing)

        assert(room.isLeft)
      }

      "there isn't enough time for cleaning" in {
        val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

        val showing = Showing(0, 0, 14 :: 10, 2.hours)
        val room    = roomWithShowings.bookShowing(showing)

        assert(room.isLeft)
      }
    }
  }

}
