package cinema.domain.timeslot

import cinema.domain.movie.Movie

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration
import cinema.domain.timeslot.attribute.Attribute
import cinema.domain.timeslot.attribute.Require3DGlasses

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

  def apply(startTime: OffsetDateTime, movie: Movie): Showing = {
    new Showing(0, movie.id, startTime, movie.duration)
      .withAttribute(Option.when(movie.is3D)(Require3DGlasses))
  }

}
