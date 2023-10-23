package cinema.room;

import cinema.room.Room;

import java.time.Period;

public class RoomRepository {
    public Room findOne(int roomId) {
        return new Room(1, 30);
    }
}
