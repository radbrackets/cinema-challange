package net.skarbek.cinemachallange.domain.model;

import org.springframework.util.Assert;

import java.util.Objects;

public class MovieId {

    private final String value;

    public MovieId(String value) {
        Assert.notNull(value, "MovieId cannot be null");
        this.value = value;
    }

    public String stringValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieId movieId = (MovieId) o;
        return Objects.equals(value, movieId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
