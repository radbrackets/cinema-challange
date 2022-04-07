package com.highwaycinema.planner.service

data class Cinema(val rooms: List<Room>){
    fun findAvailableRoomsForMovie(movie: Movie) =
        rooms.filter { it.findAvailableTimeRangesForMovie(movie).isNotEmpty() }

    fun findAvailableStartRangesForMovie(movie: Movie) =
        findAvailableRoomsForMovie(movie)
            .associate { it.name to it.findAvailableStartingTimeRangesForMovie(movie) }

}