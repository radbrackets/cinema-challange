package com.highwaycinema.planner.service

class PlannerService {

    fun getAvailableRoomsForMovieInCinema(cinema: Cinema, movie: Movie): List<Room> =
        cinema.findAvailableRoomsForMovie(movie)


}