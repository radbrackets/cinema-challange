package net.skarbek.cinemachallange.api.model;

import java.time.LocalTime;

public class AddSeansRequest {

    private final String movieId;
    private final String roomId;
    private final LocalTime seansStartTime;

    public AddSeansRequest(String movieId, String roomId, LocalTime seansStartTime) {
        this.movieId = movieId;
        this.roomId = roomId;
        this.seansStartTime = seansStartTime;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getRoomId() {
        return roomId;
    }

    public LocalTime getSeansStartTime() {
        return seansStartTime;
    }
}
