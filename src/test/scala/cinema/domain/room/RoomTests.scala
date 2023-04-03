package cinema.domain.room

import cinema.domain.Minute
import cinema.domain.Hour
import cinema.domain.timeslot.Cleaning
import cinema.domain.timeslot.Showing
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration._

class RoomTests extends AnyFreeSpec with Matchers {

  "Room should" - {
    "add showing to empty timeslots" in {
      val showing = Showing(0, 0, 15 :: 30, 1.hours)

      val timeslots = anyRoom.bookShowing(showing).bookedTimeslots

      timeslots should contain(showing)
    }

    "add cleaning time after showing" in {
      val aRoom   = Room(0, cleaningDuration, List.empty)
      val showing = Showing(0, 0, 15 :: 30, 1.hours)

      val bookedTimeslots = aRoom.bookShowing(showing).bookedTimeslots

      bookedTimeslots should contain(Cleaning(16 :: 30, cleaningDuration))
    }

    "correctly book showing if it fits into timeslot" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val showing = Showing(0, 0, 14 :: 10, 1.hours)

      val bookedTimeslots = roomWithShowings.bookShowing(showing).bookedTimeslots

      bookedTimeslots should have length (preBookedSlots.length + 2)
      bookedTimeslots should contain(showing)
      bookedTimeslots should contain(Cleaning(15 :: 10, cleaningDuration))
    }

    "throw validation exception when showing timeslot is occupied" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val showing = Showing(0, 0, 15 :: 10, 2.hours)

      assertThrows[RuntimeException](roomWithShowings.bookShowing(showing).bookedTimeslots)
    }

    "throw validation exception when there is no enough time for cleaning" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val showing = Showing(0, 0, 14 :: 10, 2.hours)

      assertThrows[RuntimeException](roomWithShowings.bookShowing(showing).bookedTimeslots)
    }
  }

  private val cleaningDuration = 30.minutes
  private val anyRoom          = Room(0, cleaningDuration, List.empty)

  private val preBookedSlots =
    Showing(0, 0, 12 :: 30, 1.hours) ::
      Cleaning(0, 13 :: 30, cleaningDuration) ::
      Showing(0, 0, 16 :: 30, 1.hours) ::
      Cleaning(0, 17 :: 30, cleaningDuration) :: Nil

}
