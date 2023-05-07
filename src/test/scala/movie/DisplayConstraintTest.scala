package com.velocit.cinema
package movie

import org.joda.time.{DateTime, LocalTime}
import org.scalatest.funsuite.AnyFunSuite

class DisplayConstraintTest extends AnyFunSuite {
  private val displayConstraint = DisplayConstraint(new LocalTime(17, 0), new LocalTime(20, 0))

  test("should return true if date has time within constraint") {
    val dateTime = new DateTime(2023, 5, 7, 18, 30, 0)
    val result = displayConstraint.satisfyConstraint(dateTime)
    assert(result)
  }

  test("should return true if date has time at the edges of constraints") {
    val start = new DateTime(2023, 5, 7, 17, 0, 0)
    val end = new DateTime(2023, 5, 7, 20, 0, 0)
    val resultStart = displayConstraint.satisfyConstraint(start)
    val resultEnd = displayConstraint.satisfyConstraint(end)
    assert(resultStart && resultEnd)
  }

  test("should return false if date has time outside of constraint") {
    val dateTime = new DateTime(2023, 10, 12, 21, 30, 0)
    val result = displayConstraint.satisfyConstraint(dateTime)
    assert(!result)
  }

}
