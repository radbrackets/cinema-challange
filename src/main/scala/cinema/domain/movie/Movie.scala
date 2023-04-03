package cinema.domain.movie

import scala.concurrent.duration.Duration

case class Movie(
  id: Int,
  name: String,
  duration: Duration,
  is3D: Boolean = false
)
