package room.service

import room.model.Room
import java.time.Duration

class RoomService(private val roomRepository: RoomRepository) {

//    fun isRoomAvailableFor(room: Room, date: LocalDateTime, duration: Duration): Boolean = room.isRoomAvailableFor(date, duration)

    fun addRoom(room: Room): Room = roomRepository.save(room)

    fun findRoom(roomId: Long): Room {
        val room = roomRepository.findAll().find { it.roomId == roomId }
        return room.let { room } ?: throw RoomNotFoundException(roomId)
    }

    fun findAllRooms(): List<Room> = roomRepository.findAll()

    fun updateRoom(id: Long, room: Room): Room = roomRepository.update(id, room)

    fun deleteRoom(id: Long) = roomRepository.delete(id)

}

class RoomRepository {

    private var roomList: List<Room> = mutableListOf(
        Room(roomId = 1, name = "Room 1", cleaningDuration = Duration.ofMinutes(30)),
        Room(roomId = 2, name = "Room 2", cleaningDuration = Duration.ofMinutes(45)),
        Room(roomId = 3, name = "Room 3", cleaningDuration = Duration.ofHours(1)),
        Room(roomId = 4, name = "Room 4", cleaningDuration = Duration.ofMinutes(45))
    )

    fun findAll(): List<Room> {
        return roomList
    }

    fun save(room: Room): Room {
        val existingRoom = findAll().find { it.roomId == room.roomId }
        if (existingRoom == null) roomList = roomList.plus(room) else throw RoomAlreadyExistsException(room.roomId)
        return room
    }

    fun update(roomId: Long, room: Room): Room {
        roomList.find { it.roomId == roomId } ?: throw RoomNotFoundException(roomId)
        if (roomId != room.roomId) throw RoomUpdateException(roomId, room.roomId)
        delete(roomId)
        roomList = roomList.plus(room)
        return room
    }

    fun delete(roomId: Long) {
        roomList = roomList.filter { it.roomId != roomId }
    }
}

class RoomAlreadyExistsException(roomId: Long) : Exception("Room with id [$roomId] already exists!")
class RoomNotFoundException(roomId: Long) : Exception("Room with id [$roomId] not found!")
class RoomUpdateException(roomId: Long, updatedRoomId: Long) :
    Exception("Movie id mismatch: movie to update has id = [$roomId] and updated movie data has id = [$updatedRoomId]")
