package net.skarbek.cinemachallange.assertions;

import net.skarbek.cinemachallange.api.model.WeekScheduleView;
import org.assertj.core.api.Assertions;

public class DomainAssertion extends Assertions {

    public static WeekScheduleViewAssertion assertThat(WeekScheduleView actual){
        return new WeekScheduleViewAssertion(actual);
    }
}
