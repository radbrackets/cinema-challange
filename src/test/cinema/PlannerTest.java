package cinema;

import cinema.room.Room;
import cinema.room.RoomUnavailablityPlanService;
import cinema.room.RoomUnavialableException;
import cinema.show.MovieService;
import cinema.room.RoomRepository;
import cinema.show.Show;
import cinema.show.ShowRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

class PlannerTest {

    private Planner planner;
    private ShowRepository showRepository;
    private RoomUnavailablityPlanService roomUnavailablityPlanService;
    private RoomRepository roomRepository;
    private MovieService movieService;
    ArgumentCaptor<Show> showArgumentCaptor;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        showArgumentCaptor = ArgumentCaptor.forClass(Show.class);
        this.roomRepository = Mockito.spy(new RoomRepository());
        showRepository = Mockito.mock(ShowRepository.class);

        movieService = Mockito.spy(MovieService.getInstance());
        roomUnavailablityPlanService = Mockito.spy(new RoomUnavailablityPlanService() {
            @Override
            public boolean isRoomAvailable(int roomId, LocalDateTime start, LocalDateTime end) {
                return RoomUnavailablityPlanService.super.isRoomAvailable(roomId, start, end);
            }
        });
        planner = new Planner(roomRepository,showRepository, movieService, roomUnavailablityPlanService);
    }

    @Test
    void shouldThrowExceptionWhenRoomIsDisabled(){
        //given
        given(roomUnavailablityPlanService.isRoomAvailable(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(false);

        ScheduleShowCommand command = new ScheduleShowCommand(1,false,
                DayOfWeek.MONDAY, LocalTime.of(12,0),1);

        //when+then
        Assertions.assertThrows(RoomUnavialableException.class,
                ()->planner.plannerScheduleCommandHandler(command));


    }

    @ParameterizedTest
    @ValueSource(ints = {6,7,23,1})
    @DisplayName("Should not allow plan show when cinema is closed")
    void shouldntAllowPlanMovieShowWhenCinemaIsClosed(int hour){
        ScheduleShowCommand command = new ScheduleShowCommand(1,false,
                DayOfWeek.MONDAY, LocalTime.of(hour,0),1);

        var exception = Assertions.assertThrows(RuntimeException.class,
                ()->planner.plannerScheduleCommandHandler(command));
        assertEquals("Bad planed start of show",exception.getMessage());

    }

    @Test
    @DisplayName("Should not allow plan premiere before 17:00")
    void shouldntAllowPlanPremiereShowBefore17Hour(){
        ScheduleShowCommand command = new ScheduleShowCommand(1,true,
                DayOfWeek.MONDAY, LocalTime.of(12,0),1);

        var exception = Assertions.assertThrows(RuntimeException.class,
                ()->planner.plannerScheduleCommandHandler(command));
        assertEquals("Bad planed start of show",exception.getMessage());

    }

    @Test
    @DisplayName("Should allow plan premiere after 17:00")
    void shouldAllowPlanPremiereAfter17(){
        ScheduleShowCommand command = new ScheduleShowCommand(1,true,
                DayOfWeek.MONDAY, LocalTime.of(17,15),1);
        mockSaveShow(command);
        Show show = planner.plannerScheduleCommandHandler(command);
        then(show.getDayOfWeek()).isEqualTo(command.dayOfWeek());
        then(show.getTime()).isEqualTo(command.time());
        then(show.getMovie().movieId()).isEqualTo(command.movieId());
        then(show.getRoom().id()).isEqualTo(command.roomId());

    }

    private void mockSaveShow(ScheduleShowCommand command) {
        var movie = movieService.getMovie(1);
        var room = roomRepository.findOne(1);
        given(showRepository.save(any(Show.class))).willReturn(Show.of(command,movie,room));
    }

    @Test
    @DisplayName("Should not allow to plan show when another movie is running, or is cleaning slot after movie")
    void shouldNotAllowDuplicateShowOrOverlaps(){
        //given
        ScheduleShowCommand command = new ScheduleShowCommand(1,true,
                DayOfWeek.MONDAY, LocalTime.of(17,15),1);
        mockSaveShow(command);
        Show show = planner.plannerScheduleCommandHandler(command);
        given(showRepository.findShowRunningBetween(show.nextStartedAt(),show.nextEndedAt(),command.roomId())).willReturn(
                Collections.singletonList(show)
        );

        //when
        planner.plannerScheduleCommandHandler(command);
        Assertions.assertThrows(SlotUnavailableException.class,()->planner.plannerScheduleCommandHandler(command));
        System.out.println(Mockito.mockingDetails(showRepository));
    }

}