package cinema.room;

import java.time.LocalDateTime;
import java.util.Objects;

public record RoomUnavailablityPlan(int roomId, LocalDateTime start, LocalDateTime end) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomUnavailablityPlan that)) return false;
        return roomId() == that.roomId() && Objects.equals(start(), that.start()) && Objects.equals(end(), that.end());
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId(), start(), end());
    }
}
