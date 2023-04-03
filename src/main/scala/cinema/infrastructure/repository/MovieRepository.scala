package cinema.infrastructure.repository

import cinema.domain.movie.Movie

trait MovieRepository extends Repository[Movie] {
  override def get(id: Int): Movie
  override def save(movie: Movie): Movie
}
