package cinema.domain.timeslot

import cinema.domain.movie.Movie

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration
import java.time.temporal.ChronoUnit.MILLIS

case class Showing(
  id: Int,
  movieId: Int,
  startTime: OffsetDateTime,
  duration: Duration
) extends Timeslot

object Showing {

  def apply(startTime: OffsetDateTime, movie: Movie): Showing = {
    new Showing(0, movie.id, startTime, movie.duration)
  }

}
