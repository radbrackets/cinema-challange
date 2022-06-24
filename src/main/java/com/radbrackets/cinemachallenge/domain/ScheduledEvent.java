package com.radbrackets.cinemachallenge.domain;

import lombok.Value;

import java.time.Duration;
import java.time.ZonedDateTime;

import static com.radbrackets.cinemachallenge.domain.Constants.*;
import static com.radbrackets.cinemachallenge.domain.ScheduledEvent.EventType.MOVIE;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;

@Value
public class ScheduledEvent {
    String eventId;
    EventType eventType;
    ZonedDateTime eventStart;
    ZonedDateTime eventEnd;

    public static ScheduledEvent movie(String eventId, Duration movieDuration, ZonedDateTime eventStart) {
        return new ScheduledEvent(
                eventId,
                MOVIE,
                eventStart,
                eventStart.plusMinutes(movieDuration.toMinutes()).plusMinutes(MAINTENANCE_BREAK)
        );
    }

    public boolean isDuringWorkHours() {
        int dayChangeDifference = eventEnd.getDayOfMonth() - eventStart.getDayOfMonth();
        return eventStart.isAfter(eventStart.with(HOUR_OF_DAY, WORK_START_HOUR).with(MINUTE_OF_HOUR, 0))
                && eventEnd.isBefore(eventEnd.minusDays(dayChangeDifference).with(HOUR_OF_DAY, WORK_END_HOUR).with(MINUTE_OF_HOUR, 0));
    }

    public enum EventType {
        MOVIE,
    }
}
