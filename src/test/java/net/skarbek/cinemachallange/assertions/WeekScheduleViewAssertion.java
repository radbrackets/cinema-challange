package net.skarbek.cinemachallange.assertions;

import net.skarbek.cinemachallange.api.model.RoomScheduleView;
import net.skarbek.cinemachallange.api.model.WeekScheduleView;
import net.skarbek.cinemachallange.domain.model.RoomId;
import org.assertj.core.api.AbstractAssert;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WeekScheduleViewAssertion extends AbstractAssert<WeekScheduleViewAssertion, WeekScheduleView> {


    protected WeekScheduleViewAssertion(WeekScheduleView weekScheduleView) {
        super(weekScheduleView, WeekScheduleViewAssertion.class);
    }

    public RoomScheduleViewAssertion hasRoomSchedule(RoomId roomId) {
        assertThat(actual).isNotNull();
        List<RoomScheduleView> roomScheduleViews = actual.getRoomScheduleViews();
        assertThat(roomScheduleViews).isNotEmpty();
        RoomScheduleView roomScheduleView = roomScheduleViews.stream()
                .filter(r -> r.getRoomId().equals(roomId.stringValue()))
                .findFirst()
                .orElse(null);
        assertThat(roomScheduleView).isNotNull();
        return new RoomScheduleViewAssertion(roomScheduleView, this);
    }

    public WeekScheduleViewAssertion that() {
        //intentionally empty
        return this;
    }

}
