package net.skarbek.cinemachallange.api.model;

import java.util.List;

public class WeekScheduleView {

    List<RoomScheduleView> roomschedules;

    public WeekScheduleView(List<RoomScheduleView> roomScheduleViews) {
        this.roomschedules = roomScheduleViews;
    }

    public List<RoomScheduleView> getRoomScheduleViews() {
        return roomschedules;
    }
}
