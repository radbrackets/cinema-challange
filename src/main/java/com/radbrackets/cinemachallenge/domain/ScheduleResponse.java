package com.radbrackets.cinemachallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.radbrackets.cinemachallenge.domain.ScheduleResponse.ResponseCode.OK;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ScheduleResponse {
    private ResponseCode responseCode;
    private boolean is3d;

    public ScheduleResponse(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    static ScheduleResponse success(boolean isIn3d) {
        return new ScheduleResponse(OK, isIn3d);
    }

    static ScheduleResponse failed(ResponseCode responseCode) {
        return new ScheduleResponse(responseCode);
    }

    public enum ResponseCode {
        OK,
        MOVIE_IS_OVERLAPPING,
        MOVIE_OUTSIDE_WORKING_HOURS,
        ROOM_NOT_AVAILABLE
    }
}
