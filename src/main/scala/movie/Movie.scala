package com.velocit.cinema
package movie

import room.ScreenType.ScreenType

import org.joda.time.{DateTime, Duration, LocalTime}

class MovieId(val value: String) extends AnyVal

object MovieId {
  def apply(value: String) = new MovieId(value)
}

case class DisplayConstraint(start: LocalTime, end: LocalTime) {
  def satisfyConstraint(date: DateTime): Boolean = {
    val time = date.toLocalTime
    !time.isBefore(start) && !time.isAfter(end)
  }
}

/**
 *
 * @param id                 - id of the movie
 * @param title              - title of the movie
 * @param duration           - duration of the movie (in millis)
 * @param screenType         - screen type 2D/3D - movie can be only displayed in a room of the same type
 * @param displayConstraints - list of time boxes in which a movie can be played
 *                           (ie. premiere films can be played only between 17 and 20). Nil by default
 */
case class Movie(
                  id: MovieId,
                  title: String,
                  duration: Duration,
                  screenType: ScreenType,
                  displayConstraints: List[DisplayConstraint] = Nil
                ) {
  def verifyDisplayConstraints(startDate: DateTime): Boolean = displayConstraints match {
    case Nil => true
    case constraints => constraints.exists(_.satisfyConstraint(startDate))
  }
}

class MovieNotFoundException(movieId: MovieId)
  extends Exception(s"Could not found movie with $movieId") {
}

object MovieNotFoundException {
  def apply(movieId: MovieId) = new MovieNotFoundException(movieId)
}

class DisplayConstraintsNotSatisfiedException(movie: Movie, startDate: DateTime)
  extends Exception(s"Could not schedule movie ${movie.id} at $startDate because it doesn't satisfy any display constraints ${movie.displayConstraints}") {
}

object DisplayConstraintsNotSatisfiedException {
  def apply(movie: Movie, startDate: DateTime) = new DisplayConstraintsNotSatisfiedException(movie, startDate)
}