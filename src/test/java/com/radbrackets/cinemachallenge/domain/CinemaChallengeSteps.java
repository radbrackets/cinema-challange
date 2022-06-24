package com.radbrackets.cinemachallenge.domain;

import com.radbrackets.cinemachallenge.config.DomainConfiguration;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.radbrackets.cinemachallenge.domain.DataTableTransformerConfig.FORMATTER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {
        MovieRepository.class,
        RoomRepository.class,
        DomainConfiguration.class
})
@CucumberContextConfiguration
public class CinemaChallengeSteps {
    @Autowired
    @MockBean
    protected MovieRepository movieRepository;
    @Autowired
    @MockBean
    protected RoomRepository roomRepository;
    @Autowired
    private ScheduleService scheduleService;
    private ScheduleResponse resultResponse;


    @Given("there is a movie in repository")
    public void thereIsAMovieInRepository(Movie movie) {
        when(movieRepository.getReferenceById("abc123")).thenReturn(movie);
    }

    @Given("there is a room in repository with no scheduled events")
    public void thereIsARoomInRepository(DataTable dataTable) {
        Map<String, String> data = dataTable.transpose().asMap();
        Room room = new Room(
                data.get("roomId"),
                Boolean.parseBoolean(data.get("isAvailable")),
                new ArrayList<>()
        );
        when(roomRepository.getReferenceById("def123")).thenReturn(room);
    }

    @Given("there is a room in repository with scheduled event")
    public void thereIsARoomInRepositoryWithScheduledEvent(DataTable dataTable) {
        Map<String, String> data = dataTable.transpose().asMap();
        String scheduledStart1 = data.get("scheduledStart");
        String scheduledEnd1 = data.get("scheduledEnd");
        List<ScheduledEvent> scheduledEvents;
        if (scheduledStart1 != null && scheduledEnd1 != null) {
            ZonedDateTime scheduledStart = ZonedDateTime.parse(scheduledStart1, FORMATTER);
            ZonedDateTime scheduledEnd = ZonedDateTime.parse(scheduledEnd1, FORMATTER);
            ScheduledEvent scheduledEvent = new ScheduledEvent(
                    "abece",
                    ScheduledEvent.EventType.MOVIE,
                    scheduledStart,
                    scheduledEnd
            );
            scheduledEvents = Stream.of(scheduledEvent).collect(Collectors.toList());
        } else {
            scheduledEvents = new ArrayList<>();
        }
        Room room = new Room(
                data.get("roomId"),
                Boolean.parseBoolean(data.get("isAvailable")),
                scheduledEvents
        );
        when(roomRepository.getReferenceById("def123")).thenReturn(room);
    }

    @When("service is called with movie event")
    public void serviceIsCalledWithMovieEvent(ScheduleRequest scheduleRequest) {
        resultResponse = scheduleService.schedule(scheduleRequest);
    }

    @Then("service responds with data")
    public void serviceRespondsWithData(ScheduleResponse scheduleResponse) {
        assertThat(resultResponse).isNotNull();
        assertThat(resultResponse.getResponseCode()).isEqualTo(scheduleResponse.getResponseCode());
        assertThat(resultResponse.is3d()).isEqualTo(scheduleResponse.is3d());
    }
}
