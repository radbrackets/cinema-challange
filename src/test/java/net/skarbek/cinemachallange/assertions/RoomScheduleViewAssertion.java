package net.skarbek.cinemachallange.assertions;

import net.skarbek.cinemachallange.api.model.RoomScheduleView;
import net.skarbek.cinemachallange.api.model.SeanceView;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.time.LocalTime;
import java.util.List;

public class RoomScheduleViewAssertion extends AbstractAssert<RoomScheduleViewAssertion, RoomScheduleView> {

    private WeekScheduleViewAssertion root;

    protected RoomScheduleViewAssertion(RoomScheduleView roomScheduleView,WeekScheduleViewAssertion root) {
        super(roomScheduleView, RoomScheduleViewAssertion.class);
        this.root = root;
    }

    public SeanceViewAssertion hasSeansThatStartsAt(LocalTime expected) {
        List<SeanceView> seanceViews = this.actual.getSeances();
        Assertions.assertThat(seanceViews).isNotEmpty();
        SeanceView seanceView = seanceViews.stream()
                .filter(s -> s.getStartTime().equals(expected))
                .findFirst()
                .orElse(null);
        Assertions.assertThat(seanceView).isNotNull();
        return new SeanceViewAssertion(seanceView, this);
    }

    public RoomScheduleViewAssertion hasRoomLabel(String expected){
        Assertions.assertThat(actual.getRoomLabel()).isEqualTo(expected);
        return this;
    }

    public RoomScheduleViewAssertion that() {
        //intentionally empty
        return this;
    }

    public WeekScheduleViewAssertion and() {
        return root;
    }
}


