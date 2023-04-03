package cinema.domain.room

import cinema.domain._
import cinema.domain.timeslot.Cleaning
import cinema.domain.timeslot.Showing
import cinema.domain.timeslot.Unavailable
import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration._

class RoomTests extends AnyFreeSpec with Matchers with EitherValues {

  "Room should" - {
    "add showing to empty timeslots" in {
      val showing = Showing(0, 0, 15 :: 30, 1.hours)

      val room = anyRoom.bookShowing(showing)

      room.value.bookedTimeslots should contain(showing)
    }

    "add cleaning time after showing" in {
      val showing = Showing(0, 0, 15 :: 30, 1.hours)

      val room = anyRoom.bookShowing(showing)

      room.value.bookedTimeslots should contain(Cleaning(16 :: 30, cleaningDuration))
    }

    "correctly book showing if it fits into timeslot" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val showing = Showing(0, 0, 14 :: 10, 1.hours)

      val room = roomWithShowings.bookShowing(showing)

      room.value.bookedTimeslots should have length (preBookedSlots.length + 2)
      room.value.bookedTimeslots should contain(showing)
      room.value.bookedTimeslots should contain(Cleaning(15 :: 10, cleaningDuration))
    }

    "throw validation exception when showing timeslot is occupied" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val showing = Showing(0, 0, 15 :: 10, 2.hours)
      val room    = roomWithShowings.bookShowing(showing)

      assert(room.isLeft)
    }

    "throw validation exception when there is no enough time for cleaning" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val showing = Showing(0, 0, 14 :: 10, 2.hours)
      val room    = roomWithShowings.bookShowing(showing)

      assert(room.isLeft)
    }

    "mark room as unavailable" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val unavailable = Unavailable(0, 14 :: 30, 2.hours)

      val room = roomWithShowings.markRoomAsUnavailable(unavailable)

      room.value.bookedTimeslots should have length (preBookedSlots.length + 1)
      room.value.bookedTimeslots should contain(unavailable)
    }

    "throw validation exception when marking room as unavailable but timeslot is occupied" in {
      val roomWithShowings = Room(0, cleaningDuration, preBookedSlots)

      val unavailable = Unavailable(0, 12 :: 30, 8.hours)
      val room        = roomWithShowings.markRoomAsUnavailable(unavailable)

      assert(room.isLeft)
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
