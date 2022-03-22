package net.skarbek.cinemachallange.infrastructure.fake;

import net.skarbek.cinemachallange.domain.model.Room;
import net.skarbek.cinemachallange.domain.model.RoomId;
import net.skarbek.cinemachallange.infrastructure.RoomClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//Temporary, move to tests package after implement real client
@Component
public class FakeRoomClient implements RoomClient, TestClearableSource {

    private static final Map<RoomId, Room> ROOMS = new HashMap<>();

    @Override
    public Optional<Room> getRoomById(RoomId roomId) {
        Room room = ROOMS.getOrDefault(roomId, null);
        return Optional.ofNullable(room);
    }

    public void addRoom(Room room) {
        ROOMS.put(room.getId(), room);
    }


    @Override
    public void cleanUp() {
        ROOMS.clear();
    }
}
