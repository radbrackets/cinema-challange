package com.velocit.cinema
package room

import room.ScreenType.{Screen2D, Screen3D}

import org.joda.time.{DateTime, Duration, Interval}
import org.scalatest.funsuite.AnyFunSuite

class RoomTest extends AnyFunSuite {
  private val room = Room(
    RoomId("1"),
    "Imax",
    Duration.standardMinutes(20),
    Screen3D,
    List(
      new Interval(
        new DateTime(2023, 5, 7, 17, 0, 0),
        new DateTime(2023, 5, 9, 17, 0, 0)
      ),
      new Interval(
        new DateTime(2023, 5, 10, 12, 0, 0),
        new DateTime(2023, 5, 10, 17, 0, 0)
      )
    )
  )

  test("hasMatchingScreenType should correctly check for matching screen type") {
    val result1 = room.hasMatchingScreenType(Screen3D)
    val result2 = room.hasMatchingScreenType(Screen2D)
    assert(result1 && !result2)
  }

  test("canHostForInterval should return true if none of the impossibilities overlap the interval") {
    val interval = new Interval(
      new DateTime(2023, 5, 11, 17, 0, 0),
      new DateTime(2023, 5, 12, 17, 0, 0)
    )
    val result = room.canHostForInterval(interval)
    assert(result)
  }

  test("canHostForInterval should return false if at least of the impossibilities overlap the interval") {
    val interval = new Interval(
      new DateTime(2023, 5, 10, 14, 0, 0),
      new DateTime(2023, 5, 10, 17, 0, 0)
    )
    val result = room.canHostForInterval(interval)
    assert(!result)
  }
}
