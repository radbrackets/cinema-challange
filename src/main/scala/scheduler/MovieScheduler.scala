package com.velocit.cinema
package scheduler

import employee.{Employee, EmployeeId}
import movie.{DisplayConstraintsNotSatisfiedException, Movie, MovieId}
import room.{Room, RoomId, RoomImpossibilityException}

import org.joda.time.{DateTime, Interval, LocalTime}

import scala.util.{Random, Try}

class MovieEventId(val value: String) extends AnyVal

object MovieEventId {
  def apply(value: String) = new MovieEventId(value)
}

/**
 * @param id             - id of the event
 * @param movieId        - id of the displayed movie for this event
 * @param roomId         - id of the room in which this event is happening
 * @param employeeId     - id of the employee who is an owner of this event
 * @param movieStartDate - event start date+time (starts the movie)
 * @param movieEndDate   - date+time when the movie is ending and when cleaning is starting
 * @param eventEndDate   - date+time when cleaning is done and entire event is ending
 */
case class MovieEvent(id: MovieEventId,
                      movieId: MovieId,
                      roomId: RoomId,
                      employeeId: EmployeeId,
                      movieStartDate: DateTime,
                      movieEndDate: DateTime,
                      eventEndDate: DateTime
                     )

class MovieScheduler(val openingHour: LocalTime, val closingHour: LocalTime) {
  private val eventRepository = new MovieEventRepository

  /**
   * Tries to schedule a movie in a room at given time. It is synchronized to avoid concurrent movie scheduling.
   * - computes endDate for a movie and endDate for entire event (movie time + cleaning time)
   * - verifies if startDate is within opening hours of the cinema
   * - verifies if startDate satisfies DisplayConstraints of the Movie
   * - verifies if Movie and Room have matching screens
   * - verifies if startDate is not within any of the Room's impossibilities
   * - tries to schedule event and returns it if it is successful (no overlaping events in the same room)
   */
  def scheduleMovie(movie: Movie, room: Room, owner: Employee, startDate: DateTime): Try[MovieEvent] = synchronized {
    val endMovieDate = startDate.plus(movie.duration)
    val endEventDate = endMovieDate.plus(room.cleaningTime)
    val eventInterval = new Interval(startDate, endEventDate)
    for {
      _ <- Try(require(isWithinOpeningHours(startDate))).mapException(NotWithinOpeningHoursException(openingHour, closingHour, startDate))
      _ <- Try(require(movie.verifyDisplayConstraints(startDate))).mapException(DisplayConstraintsNotSatisfiedException(movie, startDate))
      _ <- Try(require(room.hasMatchingScreenType(movie.screenType))).mapException(ScreenTypeMismatchException(room, movie))
      _ <- Try(require(room.canHostForInterval(eventInterval))).mapException(RoomImpossibilityException(room, eventInterval))
      _ <- Try(require(eventRepository.canBeScheduledInTheRoom(startDate, endEventDate, room.id)))
        .mapException(MovieSchedulingException(startDate, endEventDate, room.id, movie.id))
      id = MovieEventId(Random.alphanumeric.take(10).toString())
      event = MovieEvent(id, movie.id, room.id, owner.id, startDate, endMovieDate, endEventDate)
    } yield {
      eventRepository.addMovieEvent(event)
      event
    }
  }

  private def isWithinOpeningHours(dateTime: DateTime) = {
    val time = dateTime.toLocalTime
    !time.isBefore(openingHour) && !time.isAfter(closingHour)
  }
}

class ScreenTypeMismatchException(room: Room, movie: Movie)
  extends Exception(s"Movie ${movie.id} has mismatching ScreenType (${movie.screenType}) with Room (${room.id}), which is ${room.screenType}") {
}

object ScreenTypeMismatchException {
  def apply(room: Room, movie: Movie) = new ScreenTypeMismatchException(room, movie)
}

class NotWithinOpeningHoursException(openingHour: LocalTime, closingHour: LocalTime, dateTime: DateTime)
  extends Exception(s"Cannot schedule movie which doesn't start ($dateTime) within cinema's opening hours $openingHour - $closingHour") {
}

object NotWithinOpeningHoursException {
  def apply(openingHour: LocalTime, closingHour: LocalTime, dateTime: DateTime) = new NotWithinOpeningHoursException(openingHour, closingHour, dateTime)
}

class MovieSchedulingException(startDate: DateTime, endDate: DateTime, roomId: RoomId, movieId: MovieId)
  extends Exception(s"Could not schedule movie $movieId in room $roomId between $startDate and $endDate because there is already an overlapping movie there") {
}

object MovieSchedulingException {
  def apply(startDate: DateTime, endEventDate: DateTime, roomId: RoomId, movieId: MovieId) =
    new MovieSchedulingException(startDate, endEventDate, roomId, movieId)
}
