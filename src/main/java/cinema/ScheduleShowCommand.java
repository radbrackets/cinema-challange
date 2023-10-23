package cinema;

import java.time.LocalTime;

public record ScheduleShowCommand (int movieId,
                                   boolean premiere,
                                   java.time.DayOfWeek dayOfWeek,
                                   LocalTime time,
                                   int roomId) {


}
