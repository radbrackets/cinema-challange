package movie.service

import movie.model.Movie
import java.time.Duration

class MovieService(private val movieRepository: MovieRepository) {

    fun findAllMovies() = movieRepository.findAll()

    fun findMovie(movieId: Long): Movie {
        val movie = movieRepository.findAll().find { it.movieId == movieId }
        return movie.let { movie } ?: throw MovieNotFoundException(movieId)
    }

    fun saveMovie(movie: Movie) = movieRepository.save(movie)

    fun updateMovie(id: Long, movie: Movie) = movieRepository.update(id, movie)

    fun deleteMovie(id: Long) = movieRepository.delete(id)
}

class MovieRepository {

    private var movieList: List<Movie> = mutableListOf(
        Movie(
            movieId = 1,
            movieName = "Shawshank Redemption",
            duration = Duration.ofHours(1).plusMinutes(30),
            requires3dGlasses = false,
            isPremiere = false
        ),
        Movie(
            movieId = 2,
            movieName = "The Matrix Resurrections",
            duration = Duration.ofHours(2).plusMinutes(15),
            requires3dGlasses = false,
            isPremiere = true
        ),
        Movie(
            movieId = 3,
            movieName = "Wsciekłe Pięści Węża",
            duration = Duration.ofHours(1).plusMinutes(30),
            requires3dGlasses = true,
            isPremiere = true
        ),
        Movie(
            movieId = 4,
            movieName = "Sarnie Żniwo",
            duration = Duration.ofHours(1).plusMinutes(45),
            requires3dGlasses = true,
            isPremiere = false
        )
    )

    fun findAll(): List<Movie> = movieList

    fun save(movie: Movie): Movie {
        val existingMovie = movieList.find { it.movieId == movie.movieId }
        if (existingMovie == null) movieList =
            movieList.plus(movie) else throw MovieAlreadyExistsException(movie.movieId)
        return movie
    }

    fun update(movieId: Long, movie: Movie): Movie {
        movieList.find { it.movieId == movieId } ?: throw MovieNotFoundException(movieId)
        if (movieId != movie.movieId) throw MovieUpdateException(movieId, movie.movieId)
        delete(movieId)
        movieList = movieList.plus(movie)
        return movie
    }

    fun delete(id: Long) {
        movieList = movieList.filter { it.movieId != id }
    }
}

class MovieAlreadyExistsException(movieId: Long) : Exception("Movie with id [$movieId] already exists!")
class MovieUpdateException(movieId: Long, updatedMovieId: Long) :
    Exception("Movie id mismatch: movie to update has id = [$movieId] and updated movie data has id = [$updatedMovieId]")
class MovieNotFoundException(movieId: Long) : Exception("Movie with id [$movieId] not found!")
