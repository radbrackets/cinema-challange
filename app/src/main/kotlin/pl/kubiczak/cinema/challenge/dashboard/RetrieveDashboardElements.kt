package pl.kubiczak.cinema.challenge.dashboard

import pl.kubiczak.cinema.challenge.dashboard.ports.IRetrieveDashboardElements
import pl.kubiczak.cinema.challenge.dashboard.ports.IRetrieveDashboardElements.DashboardElement
import java.time.LocalDate

class RetrieveDashboardElements : IRetrieveDashboardElements {

    override fun forDay(day: LocalDate): List<DashboardElement> {
        TODO("Not yet implemented")
    }
}
