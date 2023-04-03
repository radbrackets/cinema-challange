package cinema.api.command

import cinema.domain.movie.Movie
import cinema.domain.movie.MovieRepository
import cinema.domain.room.Room
import cinema.domain.room.RoomRepository
import org.mockito.Mockito.when
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.should.Matchers
import org.scalatest.time.SpanSugar.convertIntToGrainOfTime
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar.mock

trait HandlerTestSupport extends AnyWordSpec with Matchers with BeforeAndAfter {
  val anyMovieId      = 0
  val anyMovie: Movie = Movie(anyMovieId, "Shrek", 2.hours)

  def movieRepository: MovieRepository = {
    val repository = mock[MovieRepository]
    when(repository.get(anyMovieId)).thenReturn(anyMovie)
    repository
  }

  val anyRoomId         = 1
  val mockedRoom: Room  = mock[Room]
  val updatedRoom: Room = mock[Room]

  def roomRepository: RoomRepository = {
    val repository = mock[RoomRepository]
    when(repository.get(anyRoomId)).thenReturn(mockedRoom)
    repository
  }

}
