package com.velocit.cinema
package scheduler

import employee.{Employee, EmployeeId}
import movie.{DisplayConstraint, DisplayConstraintsNotSatisfiedException, Movie, MovieId}
import room.ScreenType.{Screen2D, Screen3D}
import room.{Room, RoomId, RoomImpossibilityException}

import org.joda.time.{DateTime, Duration, Interval, LocalTime}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite


class MovieSchedulerTest extends AnyFunSuite with BeforeAndAfterEach {
  private var movieScheduler = new MovieScheduler(new LocalTime(8, 0), new LocalTime(22, 0))
  private val movie = Movie(MovieId("1"), "Tenet", Duration.standardMinutes(120), Screen3D)
  private val room = Room(RoomId("1"), "IMAX", Duration.standardMinutes(30), Screen3D)
  private val employee = Employee(EmployeeId("1"), "Patrycja")

  override def beforeEach(): Unit = {
    movieScheduler = new MovieScheduler(new LocalTime(8, 0), new LocalTime(22, 0))
  }

  test("should schedule movie with empty schedule and correctly compute end dates") {
    val startDate = new DateTime(2023, 5, 7, 11, 0, 0)
    val resultWrapped = movieScheduler.scheduleMovie(movie, room, employee, startDate)
    assert(resultWrapped.isSuccess)
    val result = resultWrapped.get
    assert(result.movieEndDate == new DateTime(2023, 5, 7, 13, 0, 0))
    assert(result.eventEndDate == new DateTime(2023, 5, 7, 13, 30, 0))
  }

  test("should fail when there trying to schedule outside of working hours") {
    val startDate = new DateTime(2023, 5, 7, 23, 0, 0)
    val result = movieScheduler.scheduleMovie(movie, room, employee, startDate)
    assertThrows[NotWithinOpeningHoursException] {
      result.get
    }
  }

  test("should fail when trying to schedule movie when display constraints are not satisfied") {
    val movieWithConstraints = movie.copy(
      displayConstraints = List(
        DisplayConstraint(
          new LocalTime(17, 0),
          new LocalTime(20, 0)
        )
      )
    )
    val startDate = new DateTime(2023, 5, 7, 16, 0, 0)
    val result = movieScheduler.scheduleMovie(movieWithConstraints, room, employee, startDate)
    assertThrows[DisplayConstraintsNotSatisfiedException] {
      result.get
    }
  }

  test("should fail when trying screen type is a mismatch") {
    val movie2D = movie.copy(screenType = Screen2D)
    val startDate = new DateTime(2023, 5, 7, 16, 0, 0)
    val result = movieScheduler.scheduleMovie(movie2D, room, employee, startDate)
    assertThrows[ScreenTypeMismatchException] {
      result.get
    }
  }

  test("should fail when trying to schedule within room impossibility") {
    val roomWithImpossibilities = room.copy(
      impossibilities = List(
        new Interval(
          new DateTime(2023, 5, 7, 16, 0, 0),
          new DateTime(2023, 5, 9, 16, 0, 0)
        )
      )
    )
    val startDate = new DateTime(2023, 5, 8, 16, 0, 0)
    val result = movieScheduler.scheduleMovie(movie, roomWithImpossibilities, employee, startDate)
    assertThrows[RoomImpossibilityException] {
      result.get
    }
  }

  test("should fail when scheduling itself fails because of events overlapping") {
    val startDate = new DateTime(2023, 5, 8, 16, 0, 0)
    movieScheduler.scheduleMovie(movie, room, employee, startDate)
    val result = movieScheduler.scheduleMovie(movie, room, employee, startDate)
    assertThrows[MovieSchedulingException] {
      result.get
    }
  }
}
