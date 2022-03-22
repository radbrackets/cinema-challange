package net.skarbek.cinemachallange.domain.model;

import net.skarbek.cinemachallange.configuration.CinemaSpecification;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalTime;

public class Seance {

    private final Movie movie;
    private final CleaningOverhead cleaningOverhead;
    private final LocalTime startTime;

    public Seance(Movie movie, CleaningOverhead cleaningOverhead, LocalTime startTime, CinemaSpecification cinemaSpecification) {
        assertSeansStartsInWorkingHours(startTime, cinemaSpecification);
        assertSeansEndsInWorkingHours(startTime, movie, cleaningOverhead, cinemaSpecification);

        this.movie = movie;
        this.cleaningOverhead = cleaningOverhead;
        this.startTime = startTime;

    }

    private void assertSeansEndsInWorkingHours(LocalTime startTime, Movie movie, CleaningOverhead cleaningOverhead, CinemaSpecification cinemaSpecification) {
        Duration totalDuration = totalDuration(movie, cleaningOverhead);
        LocalTime closeTime = cinemaSpecification.getCloseTime();
        boolean isBeforeCloseTime = startTime.plus(totalDuration).isBefore(closeTime);
        boolean isEqualCloseTime = startTime.plus(totalDuration).equals(closeTime);
        Assert.isTrue(isBeforeCloseTime || isEqualCloseTime, "Seance has to end in working hours");
    }

    private void assertSeansStartsInWorkingHours(LocalTime startTime, CinemaSpecification cinemaSpecification) {
        boolean isAfterStartTime = startTime.isAfter(cinemaSpecification.getOpenTime());
        boolean isEqualStartTime = startTime.equals(cinemaSpecification.getOpenTime());
        Assert.isTrue(isAfterStartTime || isEqualStartTime, "Seance has to starts in working hours");
    }


    private Duration totalDuration(Movie movie, CleaningOverhead cleaningOverhead) {
        return movie.getDuration().plus(cleaningOverhead.getDuration());

    }

    public boolean overlappsWith(Seance newSeance) {
         return !(this.startTime.isAfter(newSeance.getCleaningEndTime()) ||
                 this.getCleaningEndTime().isBefore(newSeance.startTime));
    }

    public LocalTime getCleaningEndTime() {
        return this.startTime.plus(totalDuration(this.movie, this.cleaningOverhead));
    }

    public LocalTime getMovieEndTime() {
        return this.startTime.plus(movie.getDuration());
    }

    public Movie getMovie() {
        return movie;
    }

    public CleaningOverhead getCleaningOverhead() {
        return cleaningOverhead;
    }

    public LocalTime getStartTime() {
        return startTime;
    }
}
