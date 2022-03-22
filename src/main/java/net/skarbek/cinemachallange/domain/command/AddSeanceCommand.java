package net.skarbek.cinemachallange.domain.command;

import net.skarbek.cinemachallange.configuration.CinemaSpecification;
import net.skarbek.cinemachallange.domain.model.Movie;
import net.skarbek.cinemachallange.domain.model.Room;

import java.time.LocalTime;

public class AddSeanceCommand {

    private final Room room;
    private final Movie movie;
    private final LocalTime seansStartTime;
    private final CinemaSpecification cinemaSpecification;

    public AddSeanceCommand(Room room, Movie movie, LocalTime seansStartTime, CinemaSpecification cinemaSpecification) {
        this.room = room;
        this.movie = movie;
        this.seansStartTime = seansStartTime;
        this.cinemaSpecification = cinemaSpecification;
    }

    public Room getRoom() {
        return room;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalTime getSeansStartTime() {
        return seansStartTime;
    }

    public CinemaSpecification getCinemaSpecification() {
        return cinemaSpecification;
    }


}
