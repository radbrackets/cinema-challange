package pl.kubiczak.cinema.challenge.screenings.ports

import java.time.LocalDate

interface IRetrieveScreenings {

    fun forDay(day: LocalDate): List<Screening>
}
