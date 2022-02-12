package pl.kubiczak.cinema.challenge.jadwiga.screenings.ports

import java.util.*

interface ScreeningChanges {

    fun create(screening: Screening)

    fun update(id: UUID, screening: Screening)

    fun delete(id: UUID)
}
