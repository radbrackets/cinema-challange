package cinema.domain.core

import io.jvm.uuid.UUID

trait Repository[T] {
  def get(id: UUID): T
  def save(t: T): T
}
