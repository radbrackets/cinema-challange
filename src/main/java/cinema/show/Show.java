package cinema.show;

import cinema.ScheduleShowCommand;
import cinema.room.Room;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class Show {

    public static Show of(ScheduleShowCommand command, Movie movie, Room room){
        if(command.premiere()){
            return new Premiere(command.dayOfWeek(),command.time(),movie,room);
        } else {
            return new Show(command.dayOfWeek(),command.time(),movie,room);
        }
    }

    private int id;
    private final java.time.DayOfWeek dayOfWeek;
    private final LocalTime time;
    private final Movie movie;
    private final Room room;

    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;

    public Show(DayOfWeek dayOfWeek, LocalTime time, Movie movie, Room room) {
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.movie = movie;
        this.room = room;
        this.startedAt=LocalDateTime.now().with(TemporalAdjusters.next(dayOfWeek)).with(time);
        this.endedAt=startedAt.plusMinutes(getMovie().time()+getRoom().cleaningTime()+1);
    }

    public int getMovieId() {
        return movie.movieId();
    }

    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public int getId() {
        return id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDateTime nextStartedAt(){
        return startedAt;
    }


    public LocalDateTime nextEndedAt(){
        return endedAt;
    }

    public boolean startCanBeScheduled(){
        return time.isAfter(LocalTime.of(8,0)) && time.isBefore(LocalTime.of(22,0));
    }

}
