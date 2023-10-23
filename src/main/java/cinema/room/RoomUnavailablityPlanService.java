package cinema.room;

import java.time.LocalDateTime;

public interface RoomUnavailablityPlanService {
    //repozytorium do komunikacji z bazą danych
    default boolean isRoomAvailable(int roomId, LocalDateTime start, LocalDateTime end){
        return true;
    }
}
