package room.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import room.model.Room
import java.time.Duration

class RoomServiceTest : BehaviorSpec({
    val roomService = RoomService(RoomRepository())
    given("a room") {
        val room1 = Room(5, name = "Room 5", cleaningDuration = Duration.ofHours(1))
        `when`("room is added") {
            roomService.addRoom(room1)
            then("should find that room in it") {
                val roomEntry = roomService.findRoom(5)
                roomEntry shouldNotBe null
                roomEntry.name shouldBe "Room 5"
                roomEntry.cleaningDuration shouldBe Duration.ofHours(1)
            }
        }
    }

    given("the current repository state") {
        `when`("rooms are searched for") {
            val rooms = roomService.findAllRooms()
            then("should return two rooms from it") {
                rooms.size shouldBe 5
            }
        }
    }

    given("an id") {
        val roomId = 2L
        `when`("find a room with this id") {
            val room = roomService.findRoom(roomId)
            then("room 2 data should be") {
                room.name shouldBe "Room 2"
                room.cleaningDuration shouldBe Duration.ofMinutes(45)
            }
        }
    }

    given("the same id as before") {
        val room2 = roomService.findRoom(2)
        val room2Update = room2.copy(name = "Roomba 2", cleaningDuration = Duration.ofHours(12))
        `when`("update a room with this id") {
            val updatedRoom2Id = roomService.updateRoom(room2.roomId, room2Update).roomId
            val room = roomService.findRoom(room2.roomId)
            then("assertions shall assert!") {
                room2.roomId shouldBe updatedRoom2Id
                room.name shouldBe "Roomba 2"
                room.cleaningDuration shouldBe Duration.ofHours(12)
            }
        }
    }

    given("room with id 2 and an room update with different id") {
        val room2 = roomService.findRoom(2)
        val room2Update = room2.copy(roomId = 8)
        `when`("should throw exception when given room id differs from updated room id") {
            val exception = shouldThrow<RoomUpdateException> {
                roomService.updateRoom(room2.roomId, room2Update)
            }
            then("the message should contain correct room id's") {
                exception.message shouldBe "Movie id mismatch: movie to update has id = [2] and updated movie data has id = [8]"
            }
        }
    }

    given("room with id 2 and second room with the same Id") {
        val room2 = roomService.findRoom(2)
        val newRoom = room2.copy(name = "Chillout Room")
        `when`("should throw exception when given room id differs from updated room id") {
            val exception = shouldThrow<RoomAlreadyExistsException> {
                roomService.addRoom(newRoom)
            }
            then("the message should contain correct room id's") {
                exception.message shouldBe "Room with id [2] already exists!"
            }
        }
    }

    given("room with id 2") {
        val roomId = 2L
        `when`("delete this room from repository") {
            roomService.deleteRoom(roomId)
            then("all shall fall") {
                val brooms = roomService.findAllRooms()
                brooms.size shouldBe 4
            }
        }
    }

    given("non-existing room with id 2") {
        val roomId = 2L
        `when`("delete this room from repository") {
            roomService.deleteRoom(roomId)
            then("all shall fall") {
                val exception = shouldThrow<RoomNotFoundException> {
                    roomService.findRoom(roomId)
                }
                exception.message shouldBe "Room with id [$roomId] not found!"
            }
        }
    }
})
