package com.radbrackets.cinemachallenge.domain

class SeansScheduler(private val movieRepository: MovieRepository, private val roomRepository: RoomRepository) {

    fun schedule(request: ScheduleRequest): ResponseCode {
        synchronized(this) {
            val movie = movieRepository.findMovieByTitle(request.movieId)
            val room = roomRepository.findRoomById(request.roomId)
            if (!room.isAvailable)
                return ResponseCode.ROOM_NOT_AVAILABLE

            val seans = Seans.createSeans(movie, room, request.startTime, request.isPremiere)
            if (!seans.isDuringWorkingHours || !seans.hasValidSlot)
                return ResponseCode.WRONG_TIMESLOT
            if (room.isSeansOverlapping(seans))
                return ResponseCode.SEANS_IS_OVERLAPPING

            room.seanses.add(seans)
            roomRepository.save(room)
            return ResponseCode.SCHEDULED
        }
    }
}