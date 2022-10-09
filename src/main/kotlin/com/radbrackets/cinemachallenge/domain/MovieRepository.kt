package com.radbrackets.cinemachallenge.domain

interface MovieRepository {

    fun findMovieByTitle(title: String): Movie
    fun findAll(): List<Movie>
}