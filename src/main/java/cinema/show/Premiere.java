package cinema.show;

import cinema.room.Room;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Premiere extends Show{
    public Premiere(DayOfWeek dayOfWeek, LocalTime time, Movie movie, Room room) {
        super(dayOfWeek, time, movie, room);
    }

    @Override
    public boolean startCanBeScheduled() {
        return super.startCanBeScheduled() && getTime().isAfter(LocalTime.of(17,0));
    }
}
