package movie.model

import java.time.Duration


data class Movie(
    val movieId: Long,
    val movieName: String,
    val duration: Duration,
    val isPremiere: Boolean,
    val requires3dGlasses: Boolean,
    )