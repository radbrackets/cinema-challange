package com.velocit.cinema
package movie

import room.ScreenType.Screen2D

import org.joda.time.{DateTime, Duration, LocalTime}
import org.scalatest.funsuite.AnyFunSuite

class MovieTest extends AnyFunSuite {

  private val movie = Movie(
    MovieId("1"),
    "Inception",
    Duration.standardMinutes(120),
    Screen2D,
    List(
      DisplayConstraint(new LocalTime(11, 0), new LocalTime(14, 0)),
      DisplayConstraint(new LocalTime(17, 0), new LocalTime(20, 0)),
    )
  )


  test("should return true if date has time within at least one of the constraints") {
    val dateTime1 = new DateTime(2023, 5, 7, 11, 0, 0)
    val dateTime2 = new DateTime(2023, 5, 7, 18, 30, 0)
    val result1 = movie.verifyDisplayConstraints(dateTime1)
    val result2 = movie.verifyDisplayConstraints(dateTime2)
    assert(result1 && result2)
  }

  test("should return false if date has time between none of the constraints") {
    val dateTime = new DateTime(2023, 5, 7, 15, 0, 0)
    val result = movie.verifyDisplayConstraints(dateTime)
    assert(!result)
  }


}
