package cinema.domain.room.timeslot

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration

case class Cleaning(
  id: Int,
  startTime: OffsetDateTime,
  duration: Duration
) extends Timeslot

object Cleaning {

  def apply(startTime: OffsetDateTime, duration: Duration): Cleaning = {
    new Cleaning(0, startTime, duration)
  }

}
