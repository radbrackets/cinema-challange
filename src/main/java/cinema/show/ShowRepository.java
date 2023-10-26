package cinema.show;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class ShowRepository {

    /**
     * Should find show that is running in roomId at between @see startDateTime and @see endDateTime . Show is running, means that film is running, or cleaning slot is active.
     * @param startDateTime start date of period
     * @param endDateTime end date of period
     * @param roomId id of room
     * @return optional show that running
     */
    public List<Show> findShowRunningBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int roomId){
        return Collections.emptyList();
    }

    public Show save(Show show) {
        return show;
    }
}
