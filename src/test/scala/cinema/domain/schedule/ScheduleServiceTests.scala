package cinema.domain.schedule

import cinema.domain.movie.Movie
import cinema.domain.room.Room
import cinema.domain.timeslot.Showing
import cinema.domain.timeslot.Unavailable
import cinema.infrastructure.repository.MovieRepository
import cinema.infrastructure.repository.RoomRepository
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.when
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.mockito.MockitoSugar.mock
import cinema.domain._
import org.mockito.ArgumentMatchers.any
import org.scalatest.BeforeAndAfter

import scala.concurrent.duration._

class ScheduleServiceTests extends AnyFreeSpec with Matchers with BeforeAndAfter {

  before {
    when(mockedRoom.bookShowing(any[Showing])).thenReturn(Right(updatedRoom))
    when(mockedRoom.markRoomAsUnavailable(any[Unavailable])).thenReturn(Right(updatedRoom))
  }

  after {
    reset(mockedRoom)
  }

  "Schedule service should" - {
    "save room with updated booking timeslots into db" in {
      val movieRepo = movieRepository
      val roomRepo  = roomRepository
      val service   = ScheduleService(movieRepo, roomRepo)

      service.bookShowing(14 :: 30, anyMovieId, anyRoomId)

      verify(mockedRoom, times(1)).bookShowing(any[Showing])
      verify(roomRepo, times(1)).save(updatedRoom)
    }

    "save room with unavailable timeslot into db" in {
      val movieRepo = movieRepository
      val roomRepo  = roomRepository
      val service   = ScheduleService(movieRepo, roomRepo)

      service.markRoomAsUnavailable(14 :: 30, 16 :: 30, anyRoomId)

      verify(mockedRoom, times(1)).markRoomAsUnavailable(any[Unavailable])
      verify(roomRepo, times(1)).save(updatedRoom)
    }
  }

  private val anyMovieId = 0
  private val anyMovie   = Movie(anyMovieId, "Shrek", 2.hours)

  private def movieRepository = {
    val repository = mock[MovieRepository]
    when(repository.get(anyMovieId)).thenReturn(anyMovie)
    repository
  }

  private val anyRoomId   = 1
  private val mockedRoom  = mock[Room]
  private val updatedRoom = mock[Room]

  private def roomRepository = {
    val repository = mock[RoomRepository]
    when(repository.get(anyRoomId)).thenReturn(mockedRoom)
    repository
  }

}
