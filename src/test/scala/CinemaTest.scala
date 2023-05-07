package com.velocit.cinema

import employee.{EmployeeId, EmployeeNotFoundException}
import movie.{MovieId, MovieNotFoundException}
import room.{RoomId, RoomNotFoundException}

import org.joda.time.DateTime
import org.scalatest.funsuite.AnyFunSuite

class CinemaTest extends AnyFunSuite {

  private val cinema = new Cinema

  test("Should fail when there is no such employee") {
    val actual = cinema.scheduleMovieEvent(DateTime.now(), EmployeeId("1123"), MovieId("1"), RoomId("1"))
    assertThrows[EmployeeNotFoundException] {
      actual.get
    }
  }

  test("Should fail when there is no such movie") {
    val actual = cinema.scheduleMovieEvent(DateTime.now(), EmployeeId("1"), MovieId("1123"), RoomId("1"))
    assertThrows[MovieNotFoundException] {
      actual.get
    }
  }

  test("Should fail when there is no such room") {
    val actual = cinema.scheduleMovieEvent(DateTime.now(), EmployeeId("1"), MovieId("1"), RoomId("1123"))
    assertThrows[RoomNotFoundException] {
      actual.get
    }
  }

}
