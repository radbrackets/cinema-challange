package pl.kubiczak.cinema.challenge.jadwiga.screenings

import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.IManageScreenings
import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.IStoreScreenings
import pl.kubiczak.cinema.challenge.jadwiga.screenings.ports.Screening
import java.time.LocalDate
import java.util.*

class ManageScreenings(
    private val retrieveScreenings: IRetrieveScreenings,
    private val storeScreenings: IStoreScreenings
) : IManageScreenings {

    override fun forDay(day: LocalDate): List<Screening> =
        retrieveScreenings.forDay(day)

    override fun create(screening: Screening) {
        storeScreenings.create(screening)
    }

    override fun update(id: UUID, screening: Screening) {
        storeScreenings.update(id, screening)
    }

    override fun delete(id: UUID) {
        storeScreenings.delete(id)
    }
}