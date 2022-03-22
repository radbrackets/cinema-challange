package net.skarbek.cinemachallange.infrastructure;

import net.skarbek.cinemachallange.domain.model.RoomId;
import net.skarbek.cinemachallange.domain.model.RoomSchedule;
import net.skarbek.cinemachallange.domain.model.WeekOfYear;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {


    Optional<RoomSchedule> getByWeekOfYearAndRoomId(WeekOfYear weekOfYear, RoomId roomId);

    List<RoomSchedule> getByWeekOfYear(WeekOfYear weekOfYear);

    void save(RoomSchedule weekScheduleAggregationRoot);

}
