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

  def bookShowing(startTime: OffsetDateTime, movieId: Int, roomId: Int): Either[Violations, Room] = {
    val movie: Movie = movieRepository.get(movieId)
    val room: Room   = roomRepository.get(roomId)

    for {
      showing     <- Showing(startTime, movie)
      updatedRoom <- room.bookShowing(showing)
    } yield roomRepository.save(updatedRoom)
  }

  def markRoomAsUnavailable(
    startTime: OffsetDateTime,
    endTime: OffsetDateTime,
    roomId: Int
  ): Either[Violations, Room] = {
    val room: Room  = roomRepository.get(roomId)
    val unavailable = Unavailable(startTime, endTime)

    for {
      updatedRoom <- room.markRoomAsUnavailable(unavailable)
    } yield roomRepository.save(updatedRoom)
  }

}
