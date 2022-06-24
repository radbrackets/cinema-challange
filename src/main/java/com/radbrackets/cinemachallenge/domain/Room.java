package com.radbrackets.cinemachallenge.domain;

import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Value
class Room {
    String roomId;
    boolean isAvailable;
    List<ScheduledEvent> scheduledEvents;

    boolean incomingIsOverlapping(ScheduledEvent incomingEvent) {
        ZonedDateTime movieStart = incomingEvent.getEventStart();
        ZonedDateTime movieEnd = incomingEvent.getEventEnd();
        return scheduledEvents.stream().anyMatch(scheduledEvent -> {
            ZonedDateTime eventStart = scheduledEvent.getEventStart();
            ZonedDateTime eventEnd = scheduledEvent.getEventEnd();
            return movieStart.isAfter(eventStart) || movieEnd.isBefore(eventEnd);
        });
    }
}
