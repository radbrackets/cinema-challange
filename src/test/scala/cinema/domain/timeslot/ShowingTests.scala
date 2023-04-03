package cinema.domain.timeslot

import cinema.domain.movie.Movie
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration._
import cinema.domain._
import cinema.domain.timeslot.attribute.Require3DGlasses

class ShowingTests extends AnyFreeSpec with Matchers {

  "A Showing" - {
    "should correctly handle 3D movies" in {
      val normalMovie = Movie(1, "Shrek", 2.hours)
      val movieIn3D   = Movie(1, "Avatar", 2.hours, is3D = true)

      val normalShowing     = Showing(12 :: 30, normalMovie)
      val showingFor3DMovie = Showing(12 :: 30, movieIn3D)

      normalShowing.attributes should not contain (Require3DGlasses)
      showingFor3DMovie.attributes should contain(Require3DGlasses)
    }
  }

}
