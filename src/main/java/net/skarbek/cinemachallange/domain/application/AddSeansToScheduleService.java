package net.skarbek.cinemachallange.domain.application;

import net.skarbek.cinemachallange.api.model.AddSeansRequest;
import net.skarbek.cinemachallange.configuration.CinemaSpecification;
import net.skarbek.cinemachallange.domain.command.AddSeanceCommand;
import net.skarbek.cinemachallange.domain.exception.DomainException;
import net.skarbek.cinemachallange.domain.model.*;
import net.skarbek.cinemachallange.infrastructure.MovieClient;
import net.skarbek.cinemachallange.infrastructure.RoomClient;
import net.skarbek.cinemachallange.infrastructure.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class AddSeansToScheduleService {

    private final MovieClient movieClient;
    private final RoomClient roomClient;
    private final ScheduleRepository scheduleRepository;
    private final CinemaSpecification cinemaSpecification;

    public AddSeansToScheduleService(MovieClient movieClient,
                                     RoomClient roomClient,
                                     ScheduleRepository scheduleRepository,
                                     CinemaSpecification cinemaSpecification) {
        this.movieClient = movieClient;
        this.roomClient = roomClient;
        this.scheduleRepository = scheduleRepository;
        this.cinemaSpecification = cinemaSpecification;
    }

    public void addSeans(AddSeansRequest request, WeekOfYear weekOfYear) {

        Room room = roomClient.getRoomById(new RoomId(request.getRoomId()))
                .orElseThrow(() -> new DomainException("Room not found"));
        Movie movie = movieClient.getMovieById(new MovieId(request.getMovieId()))
                .orElseThrow(() -> new DomainException("Movie not found"));

        RoomSchedule roomSchedule = scheduleRepository.getByWeekOfYearAndRoomId(weekOfYear, room.getId())
                .orElse(new RoomSchedule(weekOfYear, room));

        AddSeanceCommand addSeanceCommand = new AddSeanceCommand(
                room,
                movie,
                request.getSeansStartTime(),
                cinemaSpecification);

        roomSchedule.addSeance(addSeanceCommand);

        scheduleRepository.save(roomSchedule);
    }
}
