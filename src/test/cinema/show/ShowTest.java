package cinema.show;

import cinema.ScheduleShowCommand;
import cinema.room.Room;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ShowTest {


    @Test
    void shouldCreatePremiereShowWhenPlanningPremiere(){
        ScheduleShowCommand command = new ScheduleShowCommand(1,true,
                DayOfWeek.MONDAY, LocalTime.of(17,15),1);
        var premiere = Show.of(command,mock(Movie.class), mock(Room.class));
        BDDAssertions.then(premiere).isInstanceOf(Premiere.class);
    }


    @Test
    void shouldCreateShowWhenPlanningNormalMovie(){
        ScheduleShowCommand command = new ScheduleShowCommand(1,false,
                DayOfWeek.MONDAY, LocalTime.of(17,15),1);
        var premiere = Show.of(command,mock(Movie.class), mock(Room.class));
        BDDAssertions.then(premiere).isInstanceOf(Show.class);
        BDDAssertions.then(premiere).isNotInstanceOf(Premiere.class);
    }

    @Test
    void shouldCorrectlyCalculateNearestStartDateTime(){
        //given
        var now = LocalDateTime.now();
        Show show = new Show(DayOfWeek.MONDAY,LocalTime.of(10,0),
                new Movie(1,"Test",120,false),
                new Room(1,35));
        //when
        var start = show.nextStartedAt();
        BDDSoftAssertions bddSoftAssertions = new BDDSoftAssertions();
        bddSoftAssertions.then(start).isAfterOrEqualTo(now);
        bddSoftAssertions.then(start.getDayOfWeek()).isEqualTo(show.getDayOfWeek());
        bddSoftAssertions.then(start.getHour()).isEqualTo(show.getTime().getHour());
        bddSoftAssertions.then(start.getMinute()).isEqualTo(show.getTime().getMinute());
        bddSoftAssertions.assertAll();
    }

    @Test
    void shouldCorrectlyCalculateNearestEndDateOfShow(){
        //given
        var now = LocalDateTime.now();
        Show show = new Show(DayOfWeek.MONDAY,LocalTime.of(10,0),
                new Movie(1,"Test",120,false),
                new Room(1,35));
        //when
        var end = show.nextEndedAt();
        BDDSoftAssertions bddSoftAssertions = new BDDSoftAssertions();
        bddSoftAssertions.then(end).isAfter(show.nextStartedAt());
        var period = ChronoUnit.MINUTES.between(show.nextStartedAt(),end);
        bddSoftAssertions.then(period).isBetween(154L,156L);

    }

}