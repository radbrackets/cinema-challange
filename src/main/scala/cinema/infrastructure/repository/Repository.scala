package cinema.infrastructure.repository

trait Repository[T] {
  def get(id: Int): T
  def save(t: T): T
}
