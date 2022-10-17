package org.highwaycinemaplanner;

import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Room {
    @Getter
    private final String name;
    private final Integer cleaningSlotInMinutes;

    @Getter
    private final List<Seans> scheduledSeanses;

    public Room(String roomName, int cleaningSlotInMinutes) {
        this.name = roomName;
        this.cleaningSlotInMinutes = cleaningSlotInMinutes;
        scheduledSeanses = new ArrayList<>();
    }
    public boolean scheduleMovie(Movie movie, LocalDateTime targetStartDateTime) {
        LocalDateTime targetEndDateTime = targetStartDateTime.plusMinutes(movie.getLengthInMinutes()). plusMinutes(cleaningSlotInMinutes);
        if (doesOverlapWithAlreadyScheduledMovie(targetStartDateTime, targetEndDateTime)) {
            return false;
        }

        scheduledSeanses.add(new Seans(movie, targetStartDateTime, cleaningSlotInMinutes));

        return true;
    }

    private boolean doesOverlapWithAlreadyScheduledMovie(LocalDateTime targetStartDateTime, LocalDateTime targetEndDateTime) {
        for (Seans alreadyScheduledSeans : scheduledSeanses) {
            if (alreadyScheduledSeans.getDate().equals(targetStartDateTime)) {
                return true;
            }
            if (targetStartDateTime.isBefore(alreadyScheduledSeans.getDate()) &&
                    targetEndDateTime.isAfter(alreadyScheduledSeans.getDate())) {
                return true;
            }
            if (targetStartDateTime.isBefore(alreadyScheduledSeans.getRoomFreeingDate()) &&
                    targetEndDateTime.isAfter(alreadyScheduledSeans.getRoomFreeingDate())) {
                return true;
            }
            if (targetStartDateTime.isAfter(alreadyScheduledSeans.getDate()) &&
                    targetEndDateTime.isBefore(alreadyScheduledSeans.getRoomFreeingDate())) {
                return true;
            }

        }
        return false;
    }
}
