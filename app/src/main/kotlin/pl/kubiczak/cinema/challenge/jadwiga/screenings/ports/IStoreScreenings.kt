package pl.kubiczak.cinema.challenge.jadwiga.screenings.ports

import java.util.*

interface IStoreScreenings {

    fun create(screening: Screening)

    fun update(id: UUID, screening: Screening)

    fun delete(id: UUID)
}
