package cinema.domain.core

trait Repository[T] {
  def get(id: Int): T
  def save(t: T): T
}
