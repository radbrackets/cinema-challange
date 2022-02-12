package pl.kubiczak.cinema.challenge.movies.catalog

import java.time.Duration

class TestMovie implements Movie {

    private Duration duration

    private List<Requirement> requirements

    TestMovie(Long minutes, Requirement... requirements) {
        this.duration = Duration.ofMinutes(minutes)
        this.requirements = requirements
    }

    @Override
    List<Requirement> requirements() {
        return requirements
    }

    @Override
    Duration duration() {
        return duration
    }
}
