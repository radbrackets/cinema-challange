package com.velocit.cinema
package scheduler

import room.RoomId

import org.joda.time.{DateTime, Interval}

import scala.collection.mutable.ListBuffer

class MovieEventRepository {
  private val events: ListBuffer[MovieEvent] = ListBuffer()


  def addMovieEvent(event: MovieEvent): Unit = {
    events += event
  }

  /**
   * Checks if there are no overlapping events between passed dates in passed room
   */
  def canBeScheduledInTheRoom(start: DateTime, end: DateTime, roomId: RoomId): Boolean = {
    val interval = new Interval(start, end)
    !events
      .filter(_.roomId == roomId)
      .map(event => new Interval(event.movieStartDate, event.eventEndDate))
      .exists(_.overlaps(interval))
  }


}
