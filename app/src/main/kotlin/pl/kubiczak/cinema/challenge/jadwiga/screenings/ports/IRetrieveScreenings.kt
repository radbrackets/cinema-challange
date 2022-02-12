package pl.kubiczak.cinema.challenge.jadwiga.screenings.ports

import java.time.LocalDate

interface IRetrieveScreenings {

    fun forDay(day: LocalDate): List<Screening>
}
