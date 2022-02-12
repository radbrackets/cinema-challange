package pl.kubiczak.cinema.challenge.screenings

import pl.kubiczak.cinema.challenge.screenings.ports.IRetrieveScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.IStoreScreenings
import pl.kubiczak.cinema.challenge.screenings.ports.Screening
import java.time.LocalDate
import java.util.*

class ManageScreenings(
    // example of retrieving and string data separation
    // for DB implementation but also i.e. Kafka
    private val retrieveScreenings: IRetrieveScreenings,
    private val storeScreenings: IStoreScreenings
) : IRetrieveScreenings, IStoreScreenings {

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
