package cinema.domain.timeslot

import java.time.OffsetDateTime
import scala.concurrent.duration.Duration
import scala.concurrent.duration._

case class Unavailable(
  id: Int,
  startTime: OffsetDateTime,
  duration: Duration
) extends Timeslot

object Unavailable {

  def apply(startTime: OffsetDateTime, endTime: OffsetDateTime): Unavailable = {
    val duration = (endTime.toEpochSecond - startTime.toEpochSecond).seconds
    new Unavailable(0, startTime, duration)
  }

}
