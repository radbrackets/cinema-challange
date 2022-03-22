package net.skarbek.cinemachallange.domain.model;

import org.springframework.util.Assert;

import java.util.Objects;

public class RoomId {

    private final String value;

    public RoomId(String value) {
        Assert.notNull(value, "RoomId cannot be null");
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomId roomId = (RoomId) o;
        return Objects.equals(value, roomId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String stringValue() {
        return value;
    }
}
