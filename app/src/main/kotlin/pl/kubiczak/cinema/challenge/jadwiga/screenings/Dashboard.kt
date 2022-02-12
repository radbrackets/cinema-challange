package pl.kubiczak.cinema.challenge.jadwiga.screenings

import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.Screening
import java.time.LocalDate

interface Dashboard {

    fun forDay(day: LocalDate): List<Screening>
}
