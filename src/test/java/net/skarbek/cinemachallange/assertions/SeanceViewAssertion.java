package net.skarbek.cinemachallange.assertions;

import net.skarbek.cinemachallange.api.model.SeanceView;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.time.LocalTime;

public class SeanceViewAssertion extends AbstractAssert<SeanceViewAssertion, SeanceView> {

    private final RoomScheduleViewAssertion root;

    protected SeanceViewAssertion(SeanceView seanceView, RoomScheduleViewAssertion root) {
        super(seanceView, SeanceViewAssertion.class);
        this.root = root;
    }

    public SeanceViewAssertion hasMovieName(String expected) {
        Assertions.assertThat(actual.getMovieName()).isEqualTo(expected);
        return this;
    }

    public SeanceViewAssertion hasStartTime(LocalTime expected) {
        Assertions.assertThat(actual.getStartTime()).isEqualTo(expected);
        return this;
    }

    public SeanceViewAssertion hasMovieEndTime(LocalTime expected) {
        Assertions.assertThat(actual.getMovieEndTime()).isEqualTo(expected);
        return this;
    }

    public SeanceViewAssertion hasCleaningEndTime(LocalTime expected) {
        Assertions.assertThat(actual.getCleaningEndTime()).isEqualTo(expected);
        return this;
    }

    public SeanceViewAssertion that() {
        //intentionally empty
        return this;
    }

    public RoomScheduleViewAssertion and(){
        return root;
    }


}
