package net.skarbek.cinemachallange.infrastructure;

import net.skarbek.cinemachallange.domain.model.Room;
import net.skarbek.cinemachallange.domain.model.RoomId;

import java.util.Optional;

public interface RoomClient {

    Optional<Room> getRoomById(RoomId roomId);
}
