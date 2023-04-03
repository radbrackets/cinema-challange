package cinema.domain.movie

import cinema.domain.core.Repository

trait MovieRepository extends Repository[Movie] {
  override def get(id: Int): Movie
  override def save(movie: Movie): Movie
}
