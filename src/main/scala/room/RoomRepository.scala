package com.velocit.cinema
package room

import room.ScreenType._

import org.joda.time.Duration


object RoomRepository {
  def getAllRooms: List[Room] = List(
    Room(RoomId("1"), "IMAX", Duration.standardMinutes(30), Screen3D),
    Room(RoomId("2"), "Cinkciarz", Duration.standardMinutes(15), Screen2D),
    Room(RoomId("3"), "Kinder Bueno", Duration.standardMinutes(20), Screen3D),
    Room(RoomId("4"), "Mastercard", Duration.standardMinutes(15), Screen2D),
  )
}
