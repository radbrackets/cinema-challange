package cinema.domain.schedule

import cinema.domain.movie.Movie
import cinema.domain.room.Room
import cinema.domain.timeslot.Showing
import cinema.domain.Hour
import cinema.domain.Minute
import cinema.infrastructure.repository.MovieRepository
import cinema.infrastructure.repository.RoomRepository
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar.mock

import scala.concurrent.duration._

class ScheduleServiceTests extends AnyFreeSpec with Matchers {

  "Schedule service should" - {
    "save room with updated booking timeslots into db" in {
      val service = ScheduleService(movieRepository, roomRepository)

      service.bookShowing(14 :: 30, anyMovieId, anyRoomId)

      val updatedRoom = verify(mockedRoom, times(1)).bookShowing(any[Showing])
      verify(roomRepository, times(1)).save(updatedRoom)
    }
  }

  private val anyMovieId = 0
  private val anyMovie   = Movie(anyMovieId, "Shrek", 2.hours)

  private val movieRepository = {
    val repository = mock[MovieRepository]
    when(repository.get(anyMovieId)).thenReturn(anyMovie)
    repository
  }

  private val anyRoomId  = 1
  private val mockedRoom = mock[Room]

  private val roomRepository = {
    val repository = mock[RoomRepository]
    when(repository.get(anyRoomId)).thenReturn(mockedRoom)
    repository
  }

}
