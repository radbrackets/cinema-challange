package com.radbrackets.cinemachallenge.domain

interface RoomRepository {
    fun findRoomById(roomId: Int): Room
    fun findAll(): List<Room>
    fun save(room: Room) = room
}