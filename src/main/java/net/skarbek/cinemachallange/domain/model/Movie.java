package net.skarbek.cinemachallange.domain.model;

import org.springframework.util.Assert;

import java.time.Duration;

public class Movie {

    private final MovieId id;
    private final String name;
    private final Duration duration;

    public Movie(MovieId id, String name, Duration duration) {
        Assert.notNull(id, "MovieId cannot be null");
        Assert.notNull(name, "Movie name cannot be null");
        Assert.notNull(duration, "duration cannot be null");
        Assert.isTrue(!duration.isNegative(), "duration cannot be negative");
        Assert.isTrue(!duration.isZero(), "duration cannot be 0");
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public MovieId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Duration getDuration() {
        return duration;
    }
}
