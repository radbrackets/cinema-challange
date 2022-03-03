package movie.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import movie.model.Movie
import java.time.Duration

class MovieServiceTest : FunSpec({
    val movieService = MovieService(MovieRepository())
    test("should find all movies") {
        // when
        val movies = movieService.findAllMovies()
        // then
        movies.size shouldBe 4
    }

    test("should save new movie") {
        // given
        val newMovie = Movie(
            movieId = 5,
            movieName = "Bułgarski Pościkk",
            isPremiere = true,
            requires3dGlasses = false,
            duration = Duration.ofHours(1)
        )
        // when
        movieService.saveMovie(newMovie)
        val newMovieFromRepository = movieService.findMovie(5)
        // then
        newMovieFromRepository.movieName shouldBe "Bułgarski Pościkk"
        newMovieFromRepository.duration shouldBe Duration.ofHours(1)
        newMovieFromRepository.isPremiere shouldBe true
        newMovieFromRepository.requires3dGlasses shouldBe false
    }

    test("should throw RoomAlreadyExistsException when trying to add a movie with existing id") {
        // given
        val movie = movieService.findMovie(1)
        val newMovie = movie.copy(movieName = "Sum tak zwany olimpijczyk")
        // when
        val exception = shouldThrow<MovieAlreadyExistsException> {
            movieService.saveMovie(newMovie)
        }
        // then
        exception.message shouldBe "Movie with id [1] already exists!"
    }

    test("should update movie") {
        // given
        val movie = movieService.findMovie(1)
        val updatedMovie = movie.copy(movieName = "Wściekłe pięści węża 3")
        // when
        movieService.updateMovie(1, updatedMovie)
        // then
        val updatedMovieFromRepository = movieService.findMovie(1)
        updatedMovieFromRepository.movieName shouldBe "Wściekłe pięści węża 3"
    }

    test("should not update movie") {
        // given
        val movie = movieService.findMovie(2)
        val updatedMovie = movie.copy(movieId = 9, movieName = "Wściekłe pięści węża 4")
        // when
        val exception = shouldThrow<MovieUpdateException> {
            movieService.updateMovie(2, updatedMovie)
        }
        // then
        val updatedMovieFromRepository = movieService.findMovie(2)
        updatedMovieFromRepository.movieName shouldBe "The Matrix Resurrections"
        exception.message shouldBe "Movie id mismatch: movie to update has id = [2] and updated movie data has id = [9]"
    }

    test("should not update nonexistent movie") {
        // given
        val movie = movieService.findMovie(2)
        // when
        val exception = shouldThrow<MovieNotFoundException> {
            movieService.updateMovie(7, movie)
        }
        // then
        exception.message shouldBe "Movie with id [7] not found!"
    }

    test("should delete movie") {
        // when
        movieService.deleteMovie(3)
        val exception = shouldThrow<MovieNotFoundException> {
            movieService.findMovie(3)
        }
        val movies = movieService.findAllMovies()
        // then
        movies.size shouldBe 4
        exception.message shouldBe "Movie with id [3] not found!"
    }
})
