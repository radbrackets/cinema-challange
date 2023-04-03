package cinema.domain.room

import cinema.domain.timeslot.Cleaning
import cinema.domain.timeslot.Showing
import cinema.domain.timeslot.Timeslot
import cinema.domain.timeslot.Unavailable

import scala.concurrent.duration.Duration

//TODO Maybe should be named screen?
case class Room(
  id: Int,
  cleaningDuration: Duration,
  bookedTimeslots: List[Timeslot]
) {

  def bookShowing(showing: Showing): Room = {
    val cleaning = Cleaning(showing.endTime, cleaningDuration)

    if (isTimeslotBooked(showing))
      throw new RuntimeException("Event overlaps with already booked timeslots")

    // TODO add validation + exception types?
    if (isTimeslotBooked(cleaning))
      throw new RuntimeException("Cleaning overlaps with already booked timeslots")

    copy(bookedTimeslots = showing :: cleaning :: bookedTimeslots)
  }

  def markRoomAsUnavailable(unavailable: Unavailable): Room = {
    if (isTimeslotBooked(unavailable))
      throw new RuntimeException("Unavailable overlaps with already booked timeslots")

    copy(bookedTimeslots = unavailable :: bookedTimeslots)
  }

  private def isTimeslotBooked(timeslot: Timeslot): Boolean = {
    bookedTimeslots.exists(_.overlapsWith(timeslot))
  }

}
