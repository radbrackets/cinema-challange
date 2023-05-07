package org.highwaycinemaplanner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Cinema {
    final private List<Room> rooms;

    public Cinema() {
        rooms = new ArrayList<>();
    }

    public void addRoom(Room room) throws HighwayCinemaException {
        for(var existingRoom : rooms) {
            if(existingRoom.getName().equals(room.getName())) {
                String EXCEPTION_MESSAGE = "Room with current name already is in cinema.";
                throw new HighwayCinemaException(EXCEPTION_MESSAGE);
            }
        }
        rooms.add(room);
    }


    public Room getRoomByName(String name) {
        for (var room : rooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }

        return null;
    }

    public boolean scheduleMovie(Movie movie, LocalDateTime seansDateTime) {
        for (Room room : rooms) {
            if (room.scheduleMovie(movie, seansDateTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCinemaEmpty() {
        return rooms.isEmpty();
    }
}
