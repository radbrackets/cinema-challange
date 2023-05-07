package org.highwaycinemaplanner;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@Setter
@Getter
public class Seans {
    private Movie movie;

    private LocalDateTime date;

    private Integer cleaningTimeInMinutes;

    LocalDateTime getRoomFreeingDate() {
        return date.plusMinutes(movie.getLengthInMinutes()).plusMinutes(cleaningTimeInMinutes);
    }
}
