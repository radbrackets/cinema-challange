package com.radbrackets.cinemachallenge.config;

import com.radbrackets.cinemachallenge.domain.MovieRepository;
import com.radbrackets.cinemachallenge.domain.RoomRepository;
import com.radbrackets.cinemachallenge.domain.ScheduleService;
import com.radbrackets.cinemachallenge.domain.ScheduleServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {
    @Bean
    public ScheduleService scheduleService(MovieRepository movieRepository, RoomRepository roomRepository) {
        return new ScheduleServiceImpl(movieRepository, roomRepository);
    }
}
