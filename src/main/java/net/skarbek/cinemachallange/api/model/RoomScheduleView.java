package net.skarbek.cinemachallange.api.model;

import net.skarbek.cinemachallange.domain.model.Room;
import net.skarbek.cinemachallange.domain.model.RoomSchedule;

import java.util.List;
import java.util.stream.Collectors;

public class RoomScheduleView {

    private final String roomId;
    private final String roomLabel;
    private final List<SeanceView> seanceViews;

    public RoomScheduleView(String roomId, String roomLabel, List<SeanceView> seanceViews) {
        this.roomId = roomId;
        this.roomLabel = roomLabel;
        this.seanceViews = seanceViews;
    }

    public static RoomScheduleView fromAggregate(RoomSchedule roomSchedule) {
        Room room = roomSchedule.getRoom();
        return new RoomScheduleView(
                room.getId().stringValue(),
                room.getLabel(),
                roomSchedule.getSeances()
                        .stream()
                        .map(SeanceView::fromAggregate)
                        .collect(Collectors.toList())
        );
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomLabel() {
        return roomLabel;
    }

    public List<SeanceView> getSeances() {
        return seanceViews;
    }
}
