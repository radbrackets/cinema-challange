package com.radbrackets.cinemachallenge.domain

import java.time.Duration

class DumbRoomRepository : RoomRepository {

    private val room1 = Room(1, true, 30)
    private val room2 = Room(2, true, 10)
    private val room3 = Room(3, false, 50)
    private val rooms = mapOf(room1.roomId to room1, room2.roomId to room2, room3.roomId to room3)

    override fun findRoomById(roomId: Int) = rooms.get(roomId) ?: throw NoSuchElementException()
    override fun findAll(): List<Room> = rooms.values.toList()
}