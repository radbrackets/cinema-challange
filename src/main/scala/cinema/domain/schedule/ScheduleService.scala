package cinema.domain.schedule

import cinema.domain.movie.Movie
import cinema.domain.room.Room
import cinema.domain.timeslot.Showing
import cinema.infrastructure.repository.RoomRepository
import cinema.infrastructure.repository.MovieRepository

import java.time.OffsetDateTime

case class ScheduleService(
  movieRepository: MovieRepository,
  roomRepository: RoomRepository
) {

  def bookShowing(startTime: OffsetDateTime, movieId: Int, roomId: Int): Showing = {
    val movie: Movie = movieRepository.get(movieId)
    val room: Room   = roomRepository.get(roomId)
    val showing      = Showing(startTime, movie)

    val updatedRoom = room.bookShowing(showing)
    roomRepository.save(updatedRoom)

    showing
  }

}
