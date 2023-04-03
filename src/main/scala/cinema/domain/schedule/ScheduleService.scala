package cinema.domain.schedule

import cinema.domain.movie.Movie
import cinema.domain.room.Room
import cinema.domain.timeslot.Showing
import cinema.domain.timeslot.Unavailable
import cinema.domain.validator.Validator.Violations
import cinema.infrastructure.repository.RoomRepository
import cinema.infrastructure.repository.MovieRepository

import java.time.OffsetDateTime

case class ScheduleService(
  movieRepository: MovieRepository,
  roomRepository: RoomRepository
) {

  def bookShowing(startTime: OffsetDateTime, movieId: Int, roomId: Int): Either[Violations, Showing] = {
    val movie: Movie = movieRepository.get(movieId)
    val room: Room   = roomRepository.get(roomId)

    Showing(startTime, movie).toEither.map { showing =>
      val updatedRoom = room.bookShowing(showing)
      roomRepository.save(updatedRoom)

      showing
    }
  }

  def markRoomAsUnavailable(startTime: OffsetDateTime, endTime: OffsetDateTime, roomId: Int): Room = {
    val room: Room  = roomRepository.get(roomId)
    val unavailable = Unavailable(startTime, endTime)
    val updatedRoom = room.markRoomAsUnavailable(unavailable)
    roomRepository.save(updatedRoom)
  }

}
