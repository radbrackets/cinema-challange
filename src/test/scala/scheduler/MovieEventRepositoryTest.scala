package com.velocit.cinema
package scheduler

import employee.EmployeeId
import movie.MovieId
import room.RoomId

import org.joda.time.DateTime
import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite

class MovieEventRepositoryTest extends AnyFunSuite with BeforeAndAfterEach {

  private var movieEventRepository = new MovieEventRepository

  override def beforeEach(): Unit = {
    movieEventRepository = new MovieEventRepository
  }

  test("canBeScheduledInTheRoom should return true when schedule is empty") {
    val result = movieEventRepository.canBeScheduledInTheRoom(DateTime.now(), DateTime.now(), RoomId("1"))
    assert(result)
  }

  test("canBeScheduledInTheRoom should return true if trying to schedule movie at the same time but different room") {
    val start = new DateTime(2023, 5, 7, 18, 30, 0)
    val end = new DateTime(2023, 5, 7, 21, 30, 0)
    val result1 = movieEventRepository.canBeScheduledInTheRoom(start, end, RoomId("1"))
    assert(result1)
    movieEventRepository.addMovieEvent(MovieEvent(MovieEventId("1"), MovieId("1"), RoomId("1"), EmployeeId("1"), start, end, end))
    val result2 = movieEventRepository.canBeScheduledInTheRoom(start, end, RoomId("2"))
    assert(result2)
  }

  test("canBeScheduledInTheRoom should return false if trying to schedule movie at the same time in the same room") {
    val start = new DateTime(2023, 5, 7, 18, 30, 0)
    val end = new DateTime(2023, 5, 7, 21, 30, 0)
    val roomId = RoomId("1")
    val result1 = movieEventRepository.canBeScheduledInTheRoom(start, end, roomId)
    assert(result1)
    movieEventRepository.addMovieEvent(MovieEvent(MovieEventId("1"), MovieId("1"), roomId, EmployeeId("1"), start, end, end))
    val result2 = movieEventRepository.canBeScheduledInTheRoom(start, end, roomId)
    assert(!result2)
  }


}
