package net.skarbek.cinemachallange.infrastructure.fake;

import net.skarbek.cinemachallange.domain.model.RoomId;
import net.skarbek.cinemachallange.domain.model.RoomSchedule;
import net.skarbek.cinemachallange.domain.model.WeekOfYear;
import net.skarbek.cinemachallange.infrastructure.ScheduleRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

//Temporary. Move to tests package after implement real repository
@Component
public class InMemoryScheduleRepository implements ScheduleRepository, TestClearableSource {

    private final Map<Pair<WeekOfYear, RoomId>, RoomSchedule> inMemoRepo = new HashMap<>();

    @Override
    public Optional<RoomSchedule> getByWeekOfYearAndRoomId(WeekOfYear weekOfYear, RoomId roomId) {
        Pair<WeekOfYear, RoomId> key = Pair.of(weekOfYear, roomId);
        RoomSchedule result = inMemoRepo.getOrDefault(key, null);
        return Optional.ofNullable(result);
    }

    @Override
    public List<RoomSchedule> getByWeekOfYear(WeekOfYear weekOfYear) {
        return inMemoRepo.entrySet()
                .stream()
                .filter(e -> e.getKey().getLeft().equals(weekOfYear))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }


    @Override
    public void save(RoomSchedule roomSchedule) {
        Pair<WeekOfYear, RoomId> key = Pair.of(roomSchedule.getWeekOfYear(), roomSchedule.getRoom().getId());
        inMemoRepo.put(key, roomSchedule);
    }

    @Override
    public void cleanUp() {
        this.inMemoRepo.clear();
    }
}
