package cinema.domain.timeslot

import cinema.domain.Hour
import cinema.domain.Minute
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.concurrent.duration.FiniteDuration

class TimeslotTests extends AnyFreeSpec with Matchers {

  "Timeslot should overlap" - {
    "if first ends after second starts" in {
      val first  = timeslot(14 :: 30, 15 :: 30)
      val second = timeslot(15 :: 15, 16 :: 30)

      first overlapsWith second shouldBe true
      second overlapsWith first shouldBe true
    }

    "if first starts before second end" in {
      val first  = timeslot(15 :: 30, 17 :: 30)
      val second = timeslot(14 :: 30, 16 :: 30)

      first overlapsWith second shouldBe true
      second overlapsWith first shouldBe true
    }

    "if first includes entire second timeslot" in {
      val first  = timeslot(14 :: 30, 17 :: 30)
      val second = timeslot(15 :: 30, 16 :: 30)

      first overlapsWith second shouldBe true
      second overlapsWith first shouldBe true
    }

    "if second includes entire first timeslot" in {
      val first  = timeslot(15 :: 30, 16 :: 30)
      val second = timeslot(14 :: 30, 17 :: 30)

      first overlapsWith second shouldBe true
      second overlapsWith first shouldBe true
    }
  }

  "Timeslot should be separated" - {
    "if first is before second" in {
      val first  = timeslot(12 :: 30, 14 :: 30)
      val second = timeslot(16 :: 30, 18 :: 30)

      first overlapsWith second shouldBe false
      second overlapsWith first shouldBe false
    }

    "if first ends when second starts" in {
      val first  = timeslot(12 :: 30, 14 :: 30)
      val second = timeslot(14 :: 30, 16 :: 30)

      first overlapsWith second shouldBe false
      second overlapsWith first shouldBe false
    }
  }

  def timeslot(startDate: OffsetDateTime, endDate: OffsetDateTime): Timeslot = new Timeslot {
    override val startTime: OffsetDateTime = startDate
    override val duration: Duration = FiniteDuration(endDate.toEpochSecond - startDate.toEpochSecond, TimeUnit.SECONDS)
  }

}
