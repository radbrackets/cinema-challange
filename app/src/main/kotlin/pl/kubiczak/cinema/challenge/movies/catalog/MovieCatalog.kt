package pl.kubiczak.cinema.challenge.movies.catalog

interface MovieCatalog {

    fun forId(id: Long): Movie
}
