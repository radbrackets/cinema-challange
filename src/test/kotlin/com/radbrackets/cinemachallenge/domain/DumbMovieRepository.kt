package com.radbrackets.cinemachallenge.domain


class DumbMovieRepository : MovieRepository {
    private val godfather = Movie(title = "The Godfather", is3d = false, durationInMinutes = 175)
    private val forrest = Movie(title = "Forrest Gump", is3d = false, durationInMinutes = 142)
    private val avatar = Movie(title = "Avatar", is3d = true, durationInMinutes = 161)
    private val movies = mapOf(
        godfather.title to godfather,
        forrest.title to forrest,
        avatar.title to avatar
    )

    override fun findMovieByTitle(title: String) = movies[title] ?: throw NoSuchElementException()
    override fun findAll() = movies.values.toList()
}