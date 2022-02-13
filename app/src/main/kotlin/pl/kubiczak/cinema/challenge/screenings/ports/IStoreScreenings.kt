package pl.kubiczak.cinema.challenge.screenings.ports

import java.util.*

interface IStoreScreenings {

    fun create(screening: Screening)

    fun update(id: UUID, screening: Screening)

    fun delete(id: UUID)
}
