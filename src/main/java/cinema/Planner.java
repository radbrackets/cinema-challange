package cinema;

import cinema.room.Room;
import cinema.room.RoomRepository;
import cinema.room.RoomUnavailablityPlanService;
import cinema.room.RoomUnavialableException;
import cinema.show.Movie;
import cinema.show.MovieService;
import cinema.show.Show;
import cinema.show.ShowRepository;

import java.util.Random;

public class Planner {

    private final RoomRepository roomRepository;
    private final ShowRepository showRepository;
    private final MovieService movieService;
    private final RoomUnavailablityPlanService roomUnavailablityPlanService;

    public Planner(RoomRepository roomRepository, ShowRepository showRepository, MovieService movieService, RoomUnavailablityPlanService roomUnavailablityPlanService) {
        this.roomRepository = roomRepository;
        this.showRepository = showRepository;
        this.movieService = movieService;
        this.roomUnavailablityPlanService = roomUnavailablityPlanService;
    }

    public Show plannerScheduleCommandHandler(ScheduleShowCommand command) {

        Room room = roomRepository.findOne(command.roomId());
        Movie movie = movieService.getMovie(command.movieId());
        Show show = Show.of(command,movie,room);

        if(!show.startCanBeScheduled()){
            throw new BadTimeForStartShowException();
        }
        if(!roomUnavailablityPlanService.isRoomAvailable(room.id(), show.nextStartedAt(), show.nextEndedAt())){
            throw new RoomUnavialableException();
        }
        provideRandomDelayBeforeCheckAndWriteShow(); // adds a small random delay to minimise the chances of checking and saving the show at the same time
        return persistShow(command, show);


    }

    private void provideRandomDelayBeforeCheckAndWriteShow() {
        var random = new Random();
        var delay = random.nextInt(401)+100;
        try{
           Thread.sleep(delay);
        } catch (InterruptedException ex ){
            ex.printStackTrace();
        }
    }

    /**
     * On spring, I can use @Transactional witch isolation level serialization and rollbackFor=SlotUnavailableException, and propagation level REQUIRED_NEW or NESTED.
     */
    private Show persistShow(ScheduleShowCommand command, Show show) {
        if(showRepository.findShowRunningBetween(show.nextStartedAt(), show.nextEndedAt(), command.roomId()).size()>0){
            throw new SlotUnavailableException();
        }
        return showRepository.save(show);
    }


}
