package cinema.domain.room

import cinema.domain._
import cinema.domain.movie.Movie
import cinema.domain.room.timeslot.Cleaning
import cinema.domain.room.timeslot.Showing
import cinema.domain.room.timeslot.Timeslot
import org.scalatest.EitherValues
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.DurationInt
import scala.concurrent.duration.FiniteDuration

trait RoomTestSupport extends AnyWordSpec with Matchers with EitherValues {
  val cleaningDuration: FiniteDuration = 30.minutes
  val anyRoom: Room                    = Room(0, cleaningDuration, List.empty)
  val anyMovie: Movie                  = Movie(0, "Shrek", 2.hour)

  val preBookedSlots: List[Timeslot] =
    Showing(0, 0, 12 :: 30, 1.hours) ::
      Cleaning(0, 13 :: 30, cleaningDuration) ::
      Showing(0, 0, 16 :: 40, 1.hours) ::
      Cleaning(0, 17 :: 40, cleaningDuration) :: Nil

}
