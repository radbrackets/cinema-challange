package cinema.domain.timeslot

import cats.data.Validated
import cinema.domain.movie.Movie
import cinema.domain.timeslot.attribute.Attribute
import cinema.domain.timeslot.attribute.Require3DGlasses
import cinema.domain.validator.Validator.Violations
import cinema.domain.validator.Validator
import cinema.domain.validator.Violation

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration

case class Showing(
  id: Int,
  movieId: Int,
  startTime: OffsetDateTime,
  duration: Duration,
  attributes: List[Attribute] = Nil
) extends Timeslot {

  private def withAttribute(attribute: Option[Attribute]): Showing =
    copy(attributes = attributes ++ attribute)

}

object Showing {

  private def validate = Validator[Showing]
    .validate(_.startTime.getHour < 8)(Violation("Showing should start after 8am"))
    .validate(_.endTime.getHour > 22)(Violation("Showing should end before 10pm"))

  private def create(startTime: OffsetDateTime, movie: Movie): Showing = {
    Showing(0, movie.id, startTime, movie.duration)
      .withAttribute(Option.when(movie.is3D)(Require3DGlasses))
  }

  def apply(startTime: OffsetDateTime, movie: Movie): Validated[Violations, Showing] = {
    validate(create(startTime, movie))
  }

}
