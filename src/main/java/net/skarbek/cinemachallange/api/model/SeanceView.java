package net.skarbek.cinemachallange.api.model;

import net.skarbek.cinemachallange.domain.model.Seance;

import java.time.LocalTime;

public class SeanceView {

    private final String movieName;
    private final LocalTime startTime;
    private final LocalTime movieEndTime;
    private final LocalTime cleaningEndTime;


    public SeanceView(String movieName, LocalTime startTime, LocalTime movieEndTime, LocalTime cleaningEndTime) {
        this.movieName = movieName;
        this.startTime = startTime;
        this.movieEndTime = movieEndTime;
        this.cleaningEndTime = cleaningEndTime;
    }

    public static SeanceView fromAggregate(Seance s) {
        return new SeanceView(s.getMovie().getName(),
                s.getStartTime(),
                s.getMovieEndTime(),
                s.getCleaningEndTime());
    }

    public String getMovieName() {
        return movieName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getMovieEndTime() {
        return movieEndTime;
    }

    public LocalTime getCleaningEndTime() {
        return cleaningEndTime;
    }
}
