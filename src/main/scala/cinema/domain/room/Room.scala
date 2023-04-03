package cinema.domain.room

import cats.data.Validated
import cinema.domain.timeslot.Cleaning
import cinema.domain.timeslot.Showing
import cinema.domain.timeslot.Timeslot
import cinema.domain.timeslot.Unavailable
import cinema.domain.validator.Validator.Violations
import cinema.domain.validator.AlwaysValidValidator
import cinema.domain.validator.Validator
import cinema.domain.validator.Violation
import cats.implicits._

import scala.concurrent.duration.Duration

//TODO Maybe should be named screen?
case class Room(
  id: Int,
  cleaningDuration: Duration,
  bookedTimeslots: List[Timeslot]
) {

  private def timeslotValidator(reason: String) = Validator[Timeslot]
    .validate(isTimeslotBooked)(Violation(reason))

  private val showingValidator     = timeslotValidator("Event overlaps with already booked timeslots")
  private val cleaningValidator    = timeslotValidator("Cleaning overlaps with already booked timeslots")
  private val unavailableValidator = timeslotValidator("Unavailable overlaps with already booked timeslots")

  def bookShowing(showing: Showing): Either[Violations, Room] = {
    for {
      validShowing  <- showingValidator(showing)
      validCleaning <- cleaningValidator(Cleaning(showing.endTime, cleaningDuration))
    } yield copy(bookedTimeslots = validShowing :: validCleaning :: bookedTimeslots)
  }

  def markRoomAsUnavailable(unavailable: Unavailable): Either[Violations, Room] = {
    for {
      validUnavailable <- unavailableValidator(unavailable)
    } yield copy(bookedTimeslots = validUnavailable :: bookedTimeslots)
  }

  private def isTimeslotBooked(timeslot: Timeslot): Boolean = {
    bookedTimeslots.exists(_.overlapsWith(timeslot))
  }

}
