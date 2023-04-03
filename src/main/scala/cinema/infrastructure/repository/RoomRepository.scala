package cinema.infrastructure.repository

import cinema.domain.room.Room

trait RoomRepository extends Repository[Room] {
  override def get(id: Int): Room
  override def save(room: Room): Room
}
