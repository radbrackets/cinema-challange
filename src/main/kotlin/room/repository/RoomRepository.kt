package room.repository

import room.model.Room
import java.time.Duration

class RoomRepository {

    private var roomList: List<Room> = mutableListOf(
        Room(roomId = 1, name = "Room 1", cleaningDuration = Duration.ofMinutes(30)),
        Room(roomId = 2, name = "Room 2", cleaningDuration = Duration.ofMinutes(30)),
        Room(roomId = 3, name = "Room 3", cleaningDuration = Duration.ofMinutes(30)),
        Room(roomId = 4, name = "Room 4", cleaningDuration = Duration.ofMinutes(30))
    )

    fun findAll(): List<Room> {
        return roomList
    }

    fun save(room: Room): Room {
        val rooms = findAll().filter { it.roomId == room.roomId }
        if (rooms.isEmpty()) roomList = roomList.plus(room) else throw RoomExistsException(room.roomId)
        return room
    }

    fun update(roomId: Long, room: Room): Room {
        delete(roomId)
        roomList = roomList.plus(room)
        return room
    }

    fun delete(roomId: Long) {
        roomList = roomList.filter { it.roomId != roomId }
    }
}

class RoomExistsException(roomId: Long): Exception("Room with id [$roomId] already exists!")
