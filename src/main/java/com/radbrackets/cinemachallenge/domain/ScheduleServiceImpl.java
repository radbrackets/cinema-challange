package com.radbrackets.cinemachallenge.domain;

import java.time.ZonedDateTime;

import static com.radbrackets.cinemachallenge.domain.ScheduleResponse.ResponseCode.*;


public class ScheduleServiceImpl implements ScheduleService {
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public ScheduleServiceImpl(MovieRepository movieRepository, RoomRepository roomRepository) {
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public ScheduleResponse schedule(ScheduleRequest scheduleRequest) {
        Room room = roomRepository.getReferenceById(scheduleRequest.getRoomId());
        if (room.isAvailable()) {
            if (scheduleRequest.getMovieId() != null) {
                Movie movie = movieRepository.getReferenceById(scheduleRequest.getMovieId());
                ZonedDateTime eventStart = scheduleRequest.getEventStart();
                ScheduledEvent movieEvent = ScheduledEvent.movie(
                        scheduleRequest.getMovieId(),
                        movie.getMovieDuration(),
                        eventStart);
                if (movieEvent.isDuringWorkHours()) {
                    if (!room.incomingIsOverlapping(movieEvent)) {
                        room.getScheduledEvents().add(movieEvent);
                        roomRepository.save(room);
                        return ScheduleResponse.success(movie.isIn3d());
                    }
                    return ScheduleResponse.failed(MOVIE_IS_OVERLAPPING);
                }
                return ScheduleResponse.failed(MOVIE_OUTSIDE_WORKING_HOURS);
            }
        }
        return ScheduleResponse.failed(ROOM_NOT_AVAILABLE);
    }
}
