package pl.kubiczak.cinema.challenge.jadwiga.screenings.ports

import java.time.LocalDate
import java.util.*

interface IManageScreenings {

    fun forDay(day: LocalDate): List<Screening>

    fun create(screening: Screening)

    fun update(id: UUID, screening: Screening)

    fun delete(id: UUID)
}
