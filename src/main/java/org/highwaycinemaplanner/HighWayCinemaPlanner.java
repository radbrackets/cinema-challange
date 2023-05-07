package org.highwaycinemaplanner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class HighWayCinemaPlanner {

    private final Cinema cinema;
    private final IMovieCatalog movieCatalog;

    private final LocalTime cinemaOpenTime = LocalTime.of(7, 0);
    private final LocalTime cinemaCloseTime = LocalTime.of(23, 0);

    private final LocalTime premiereStartTime = LocalTime.of(17, 0);
    private final LocalTime premiereEndTime = LocalTime.of(21, 0);


    HighWayCinemaPlanner(Cinema cinema,
                         IMovieCatalog movieDictionary) {
        this.cinema = cinema;
        this.movieCatalog = movieDictionary;
    }

    public List<Seans> listSeanses(String roomName) {
        synchronized (cinema) {
            Room room = cinema.getRoomByName(roomName);
            if (room == null) {
                return null;
            }

            return room.getScheduledSeanses();
        }
    }

    ScheduleMovieStatus scheduleMovie(String movieName, LocalDateTime seansDateTime, boolean isPremiere) {
        synchronized (cinema) {
            if (cinema.isCinemaEmpty()) {
                return ScheduleMovieStatus.NO_ROOMS_IN_CINEMA;
            }
        }

        var movie= movieCatalog.getMovie(movieName);
        if(movie == null) {
            return ScheduleMovieStatus.MOVIE_NOT_FOUND_IN_CATALOG;
        }

        LocalDateTime targetDateCinemaOpen = LocalDateTime.of(seansDateTime.toLocalDate(), cinemaOpenTime);
        LocalDateTime targetDateCinemaClose = LocalDateTime.of(seansDateTime.toLocalDate(), cinemaCloseTime);

        if(seansDateTime.isBefore(targetDateCinemaOpen) ||
            seansDateTime.plusMinutes(movie.getLengthInMinutes()).isAfter(targetDateCinemaClose)) {
            return  ScheduleMovieStatus.MOVIE_OUTSIDE_OF_CINEMA_WORKING_HOURS;
        }

        if(isPremiere) {
            if (seansDateTime.toLocalTime().isBefore(premiereStartTime) ||
                    seansDateTime.plusMinutes(movie.getLengthInMinutes()).toLocalTime().isAfter(premiereEndTime)) {
                return ScheduleMovieStatus.MOVIE_OUTSIDE_OF_CINEMA_PREMIERE_HOURS;
            }
        }

        synchronized (cinema) {
            if (cinema.scheduleMovie(movie, seansDateTime)) {
                return ScheduleMovieStatus.MOVIE_SCHEDULED;
            }
        }

        return  ScheduleMovieStatus.NO_ROOMS_AVAILABLE_AT_GIVEN_TIME;

    }
}
