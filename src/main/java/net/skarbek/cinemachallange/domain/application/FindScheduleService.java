package net.skarbek.cinemachallange.domain.application;

import net.skarbek.cinemachallange.api.model.RoomScheduleView;
import net.skarbek.cinemachallange.api.model.WeekScheduleView;
import net.skarbek.cinemachallange.domain.model.WeekOfYear;
import net.skarbek.cinemachallange.infrastructure.ScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FindScheduleService {

    private final ScheduleRepository scheduleRepository;

    public FindScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public WeekScheduleView getSchedule(WeekOfYear weekOfYear) {
        List<RoomScheduleView> roomScheduleViews = scheduleRepository.getByWeekOfYear(weekOfYear)
                .stream()
                .map(RoomScheduleView::fromAggregate)
                .collect(Collectors.toList());
        return new WeekScheduleView(roomScheduleViews);
    }
}
