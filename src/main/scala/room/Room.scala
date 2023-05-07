package com.velocit.cinema
package room

import org.joda.time.{Duration, Interval}

class RoomId(val value: String) extends AnyVal

object RoomId {
  def apply(value: String) = new RoomId(value)
}

object ScreenType extends Enumeration {
  type ScreenType = Value
  val Screen2D, Screen3D = Value
}

/**
 *
 * @param id              - id of the room
 * @param name            - name of the room
 * @param cleaningTime    - how long it takes to clean the room (in millis)
 * @param screenType      - screen type (2D/3D) - can host movies only of the same type
 * @param impossibilities - list of intervals when no movie can be scheduled in this room
 */
case class Room(id: RoomId, name: String, cleaningTime: Duration, screenType: ScreenType.ScreenType, impossibilities: List[Interval] = Nil) {
  def hasMatchingScreenType(movieScreenType: ScreenType.ScreenType): Boolean = screenType == movieScreenType

  def canHostForInterval(interval: Interval): Boolean = impossibilities.forall(!_.overlaps(interval))
}

class RoomNotFoundException(roomId: RoomId)
  extends Exception(s"Could not found room with $roomId") {
}

object RoomNotFoundException {
  def apply(roomId: RoomId) = new RoomNotFoundException(roomId)
}

class RoomImpossibilityException(room: Room, interval: Interval)
  extends Exception(s"Could not schedule movie in room ${room.id}, because $interval overlaps with its impossibility ${room.impossibilities}") {
}

object RoomImpossibilityException {
  def apply(room: Room, interval: Interval) = new RoomImpossibilityException(room, interval)
}