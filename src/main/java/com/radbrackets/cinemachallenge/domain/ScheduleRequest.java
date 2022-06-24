package com.radbrackets.cinemachallenge.domain;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ScheduleRequest {
     String movieId;
     String roomId;
     ZonedDateTime eventStart;
}
