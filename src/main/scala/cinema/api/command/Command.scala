package cinema.api.command

import java.time.OffsetDateTime

sealed trait Command

case class BookShowingCommand(startTime: OffsetDateTime, movieId: Int, roomId: Int) extends Command
case class MarkRoomAsUnavailable(startTime: OffsetDateTime, endTime: OffsetDateTime, roomId: Int) extends Command
