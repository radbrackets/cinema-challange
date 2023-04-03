package cinema.api.command

import cinema.domain.movie.Movie
import cinema.domain.room.Room
import cinema.infrastructure.repository.MovieRepository
import cinema.infrastructure.repository.RoomRepository

case class BookShowingCommandHandler(
  movieRepository: MovieRepository,
  roomRepository: RoomRepository
) extends CommandHandler {

  override type CommandType = BookShowingCommand

  def handle(command: CommandType): Unit = {
    val movie: Movie = movieRepository.get(command.movieId)
    val room: Room   = roomRepository.get(command.roomId)
    for {
      updatedRoom <- room.bookShowing(command.startTime, movie)
    } yield roomRepository.save(updatedRoom)
  }

}
