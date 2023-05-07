package com.velocit.cinema

import employee.{Employee, EmployeeId, EmployeeNotFoundException, EmployeeRepository}
import movie.{Movie, MovieId, MovieNotFoundException, MovieRepository}
import room.{Room, RoomId, RoomNotFoundException, RoomRepository}
import scheduler.{MovieEvent, MovieScheduler}

import org.joda.time.{DateTime, LocalTime}

import scala.util.Try

/**
 * This class serves as an entrypoint for Movie Scheduler. It acts as the UI of the Movie Scheduling app, so it has
 * access to all employees, movie catalog and rooms. When a user in the UI would pick a Room, Movie and startDate, they
 * could try to schedule the MovieEvent and scheduleMovieEvent method would be called.
 */
class Cinema {
  private val employees: List[Employee] = EmployeeRepository.getAllEmployees
  private val movieCatalog: List[Movie] = MovieRepository.getMovieCatalog
  private val rooms: List[Room] = RoomRepository.getAllRooms
  private val scheduler: MovieScheduler = new MovieScheduler(new LocalTime(8, 0), new LocalTime(20, 0))

  def scheduleMovieEvent(startDate: DateTime, ownerId: EmployeeId, movieId: MovieId, roomId: RoomId): Try[MovieEvent] = {
    for {
      employee <- employees.find(_.id == ownerId).requireDefined(EmployeeNotFoundException(ownerId))
      movie <- movieCatalog.find(_.id == movieId).requireDefined(MovieNotFoundException(movieId))
      room <- rooms.find(_.id == roomId).requireDefined(RoomNotFoundException(roomId))
      event <- scheduler.scheduleMovie(movie, room, employee, startDate)
    } yield event
  }
}
