package net.skarbek.cinemachallange.aceptance.configuration;

import net.skarbek.cinemachallange.configuration.CinemaSpecification;
import net.skarbek.cinemachallange.domain.application.AddSeansToScheduleService;
import net.skarbek.cinemachallange.aceptance.helpers.GivenSupport;
import net.skarbek.cinemachallange.infrastructure.MovieClient;
import net.skarbek.cinemachallange.infrastructure.RoomClient;
import net.skarbek.cinemachallange.infrastructure.ScheduleRepository;
import net.skarbek.cinemachallange.infrastructure.fake.FakeMovieClient;
import net.skarbek.cinemachallange.infrastructure.fake.FakeRoomClient;
import net.skarbek.cinemachallange.infrastructure.fake.InMemoryScheduleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestContextConfiguration {

    @Bean
    GivenSupport givenSupport(
            FakeMovieClient fakeMovieClient,
            FakeRoomClient fakeRoomClient,
            CinemaSpecification cinemaSpecification
    ){
        return new GivenSupport(fakeMovieClient, fakeRoomClient, cinemaSpecification);
    }


    @Bean
    @Primary
    FakeMovieClient movieClient() {
        return new FakeMovieClient();
    }

    @Bean
    @Primary
    FakeRoomClient roomClient() {
        return new FakeRoomClient();
    }

    @Bean
    @Primary
    InMemoryScheduleRepository scheduleRepository() {
        return new InMemoryScheduleRepository();
    }

    @Bean
    @Primary
    AddSeansToScheduleService movieClient(
            MovieClient movieClient,
            RoomClient roomClient,
            ScheduleRepository scheduleRepository,
            CinemaSpecification cinemaSpecification
    ) {
        return new AddSeansToScheduleService(movieClient, roomClient, scheduleRepository, cinemaSpecification);
    }


}
