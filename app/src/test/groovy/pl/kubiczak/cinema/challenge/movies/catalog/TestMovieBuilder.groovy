package pl.kubiczak.cinema.challenge.movies.catalog

import java.time.Duration

import static pl.kubiczak.cinema.challenge.movies.MovieCatalog.Movie
import static pl.kubiczak.cinema.challenge.movies.MovieCatalog.Requirement

class TestMovieBuilder {

    private Long id = -1L
    private Duration duration = Duration.ofMinutes(87)
    private String title = "Romeo and Juliet"
    private List<Requirement> requirements = []

    def withId(Long id) {
        this.id = id
        this
    }

    def withDuration(Long minutes) {
        this.duration = Duration.ofMinutes(minutes)
        this
    }

    def withRequirements(List<Requirement> requirements) {
        this.requirements = requirements
        this
    }

    Movie build() {
        new Movie(
                id,
                duration,
                title,
                requirements
        )
    }

}
