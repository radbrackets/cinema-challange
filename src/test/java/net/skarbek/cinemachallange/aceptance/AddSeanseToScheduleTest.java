package net.skarbek.cinemachallange.aceptance;


import net.skarbek.cinemachallange.domain.model.CleaningOverhead;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static net.skarbek.cinemachallange.SampleData.*;
import static org.assertj.core.api.Assertions.assertThat;


public class AddSeanseToScheduleTest extends ComponentTestBase {

    @Test
    @DisplayName("Should add seance to week schedule")
    public void shouldAddSeans() {
        given.sampleMovie()
                .withId(TITANIC_ID)
                .withName(TITANIC_NAME)
                .withDuration(DURATION_120M)
                .inExternalSource();
        given.sampleRoom()
                .withId(ROOM_1_ID)
                .withLabel(ROOM_1_NAME)
                .withCleaningOverhead(CleaningOverhead.of(DURATION_30M))
                .inExternalSource();

        when.addSeansRequest()
                .forMovie(TITANIC_ID)
                .toRoomId(ROOM_1_ID)
                .withStartDate(CLOCK_12_00)
                .inWeekOfYear(WEEK_3_YEAR_2020)
                .received();

        then.thereIsScheduleFor(WEEK_3_YEAR_2020)
                .that()
                .hasRoomSchedule(ROOM_1_ID)
                .that()
                .hasRoomLabel(ROOM_1_NAME)
                .hasSeansThatStartsAt(CLOCK_12_00)
                .that()
                .hasMovieEndTime(CLOCK_14_00)
                .hasCleaningEndTime(CLOCK_14_30);
    }

    @Test
    @DisplayName("Should add two seances to one room, not overlapping")
    public void shouldAddTwoSeancesToOneRoomNotOverlapping() {
        given.sampleMovie()
                .withId(TITANIC_ID)
                .withName(TITANIC_NAME)
                .withDuration(DURATION_120M)
                .inExternalSource();
        given.sampleMovie()
                .withId(SHREK_ID)
                .withName(SHREK_NAME)
                .withDuration(DURATION_120M)
                .inExternalSource();
        given.sampleRoom()
                .withId(ROOM_1_ID)
                .withCleaningOverhead(CleaningOverhead.of(DURATION_30M))
                .inExternalSource();
        given.cinemaSpecification()
                .withOpeningTime(CLOCK_08_00)
                .withClosingTime(CLOCK_22_00);

        when.addSeansRequest()
                .forMovie(TITANIC_ID)
                .toRoomId(ROOM_1_ID)
                .withStartDate(CLOCK_12_00)
                .inWeekOfYear(WEEK_3_YEAR_2020)
                .received();
        when.addSeansRequest()
                .forMovie(SHREK_ID)
                .toRoomId(ROOM_1_ID)
                .withStartDate(CLOCK_15_00)
                .inWeekOfYear(WEEK_3_YEAR_2020)
                .received();

        then.thereIsScheduleFor(WEEK_3_YEAR_2020)
                .that()
                .hasRoomSchedule(ROOM_1_ID)
                .hasSeansThatStartsAt(CLOCK_12_00)
                .hasMovieName(TITANIC_NAME)
                .and()
                .hasSeansThatStartsAt(CLOCK_15_00)
                .hasMovieName(SHREK_NAME);

    }

    @Test
    @DisplayName("Should not allow to overlapping seances")
    public void shouldNotAllowToOverlapping() {
        given.sampleMovie()
                .withId(TITANIC_ID)
                .withName(TITANIC_NAME)
                .withDuration(DURATION_120M)
                .inExternalSource();
        given.sampleMovie()
                .withId(SHREK_ID)
                .withName(SHREK_NAME)
                .withDuration(DURATION_120M)
                .inExternalSource();
        given.sampleRoom()
                .withId(ROOM_1_ID)
                .withLabel(ROOM_1_NAME)
                .withCleaningOverhead(CleaningOverhead.of(DURATION_30M))
                .inExternalSource();

        when.addSeansRequest()
                .forMovie(TITANIC_ID)
                .toRoomId(ROOM_1_ID)
                .withStartDate(CLOCK_12_00)
                .inWeekOfYear(WEEK_3_YEAR_2020)
                .received();
        Exception exception = when.addSeansRequest()
                .forMovie(SHREK_ID)
                .toRoomId(ROOM_1_ID)
                .withStartDate(CLOCK_12_00)
                .inWeekOfYear(WEEK_3_YEAR_2020)
                .received();

        //then
        assertThat(exception)
                .isNotNull()
                .hasMessage("Seance overlapps with exisisting seanses ");

    }

    @Test
    @DisplayName("Should not allow to add seans before cinema opening")
    public void addSeansBeforeOpening() {
        given.sampleMovie()
                .withId(TITANIC_ID)
                .withName(TITANIC_NAME)
                .withDuration(DURATION_120M)
                .inExternalSource();
        given.sampleRoom()
                .withId(ROOM_1_ID)
                .withLabel(ROOM_1_NAME)
                .withCleaningOverhead(CleaningOverhead.of(DURATION_30M))
                .inExternalSource();
        given.cinemaSpecification()
                .withOpeningTime(CLOCK_12_00)
                .withClosingTime(CLOCK_22_00);

        //when
        Exception exception = when.addSeansRequest()
                .forMovie(TITANIC_ID)
                .toRoomId(ROOM_1_ID)
                .withStartDate(CLOCK_08_00)
                .inWeekOfYear(WEEK_3_YEAR_2020)
                .received();

        //then
        assertThat(exception)
                .isNotNull()
                .hasMessage("Seance has to starts in working hours");

    }

    @Test
    @DisplayName("Should not allow to add seans after cinema close")
    public void addSeanseAfterClosingTime() {
        given.sampleMovie()
                .withId(TITANIC_ID)
                .withName(TITANIC_NAME)
                .withDuration(DURATION_120M)
                .inExternalSource();
        given.sampleRoom()
                .withId(ROOM_1_ID)
                .withLabel(ROOM_1_NAME)
                .withCleaningOverhead(CleaningOverhead.of(DURATION_30M))
                .inExternalSource();
        given.cinemaSpecification()
                .withOpeningTime(CLOCK_08_00)
                .withClosingTime(CLOCK_15_00);

        //when
        Exception exception = when.addSeansRequest()
                .forMovie(TITANIC_ID)
                .toRoomId(ROOM_1_ID)
                .withStartDate(CLOCK_15_00)
                .inWeekOfYear(WEEK_3_YEAR_2020)
                .received();

        //then
        assertThat(exception)
                .isNotNull()
                .hasMessage("Seance has to end in working hours");

    }

}
