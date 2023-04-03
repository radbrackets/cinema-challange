package cinema.domain.room

import cinema.domain.movie.Movie
import cinema.domain.timeslot.Cleaning
import cinema.domain.timeslot.Showing
import cinema.domain.timeslot.Timeslot
import cinema.domain.timeslot.Unavailable
import cinema.domain.validator.Validator.Violations
import cinema.domain.validator.Validator
import cinema.domain.validator.Violation

import java.time.OffsetDateTime
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

  def bookShowing(startTime: OffsetDateTime, movie: Movie): Either[Violations, Room] = {
    for {
      showing       <- Showing(startTime, movie)
      validShowing  <- showingValidator(showing)
      validCleaning <- cleaningValidator(Cleaning(validShowing.endTime, cleaningDuration))
    } yield copy(bookedTimeslots = validShowing :: validCleaning :: bookedTimeslots)
  }

  def markRoomAsUnavailable(startTime: OffsetDateTime, endTime: OffsetDateTime): Either[Violations, Room] = {
    for {
      validUnavailable <- unavailableValidator(Unavailable(startTime, endTime))
    } yield copy(bookedTimeslots = validUnavailable :: bookedTimeslots)
  }

  private def isTimeslotBooked(timeslot: Timeslot): Boolean = {
    bookedTimeslots.exists(_.overlapsWith(timeslot))
  }

}
