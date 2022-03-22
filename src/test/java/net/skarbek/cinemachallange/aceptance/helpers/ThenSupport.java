package net.skarbek.cinemachallange.aceptance.helpers;

import net.skarbek.cinemachallange.api.model.WeekScheduleView;
import net.skarbek.cinemachallange.assertions.DomainAssertion;
import net.skarbek.cinemachallange.assertions.WeekScheduleViewAssertion;
import net.skarbek.cinemachallange.domain.application.FindScheduleService;
import net.skarbek.cinemachallange.domain.model.WeekOfYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThenSupport {

    @Autowired
    FindScheduleService findScheduleService;

    public WeekScheduleViewAssertion thereIsScheduleFor(WeekOfYear weekOfYear) {
        WeekScheduleView schedule = findScheduleService.getSchedule(weekOfYear);
        return DomainAssertion.assertThat(schedule);
    }
}
