package pl.kubiczak.cinema.challenge.movies.catalog

import java.time.Duration

interface Movie {

    fun requirements(): List<Requirement>

    fun duration(): Duration

    enum class Requirement {
        // open for discussion if this both should be here
        GLASSES_3D,
        PREMIERE
    }
}
