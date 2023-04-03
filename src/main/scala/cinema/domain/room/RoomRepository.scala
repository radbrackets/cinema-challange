package cinema.domain.room

import cinema.domain.core.Repository

trait RoomRepository extends Repository[Room] {
  override def get(id: Int): Room
  override def save(room: Room): Room
}
