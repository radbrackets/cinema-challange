package cinema.room;

import java.time.LocalDateTime;
import java.util.Objects;

public class RoomUnavailablityPlan {
    private final int roomId;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public RoomUnavailablityPlan(int roomId, LocalDateTime start, LocalDateTime end) {
        this.roomId = roomId;
        this.start = start;
        this.end = end;
    }

    public int getRoomId() {
        return roomId;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomUnavailablityPlan that)) return false;
        return getRoomId() == that.getRoomId() && Objects.equals(getStart(), that.getStart()) && Objects.equals(getEnd(), that.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomId(), getStart(), getEnd());
    }
}
