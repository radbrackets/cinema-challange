package com.radbrackets.cinemachallenge.domain

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class Seans private constructor(
    val movieTitle: String,
    val roomId: Int,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val type: SeansType
) {

    companion object {
        fun createSeans(movie: Movie, room: Room, start: LocalDateTime, isPremiere: Boolean) = Seans(
            movie.title, room.roomId, start,
            start.plus(room.breakDurationInMinutes, ChronoUnit.MINUTES).plus(movie.durationInMinutes, ChronoUnit.MINUTES),
            if (isPremiere) SeansType.PREMIERE else SeansType.NORMAL
        )
    }

    val isDuringWorkingHours: Boolean
        get() {
            val workingHours = OPENING_TIME..CLOSING_TIME
            return start.toLocalTime() in workingHours && end.toLocalTime() in workingHours;
        }

    val hasValidSlot: Boolean
        get() {
            if (type == SeansType.PREMIERE && start.toLocalTime() >= LocalTime.of(17, 0)) {
                return true
            } else if (type == SeansType.PREMIERE && start.toLocalTime() <= LocalTime.of(17, 0)) {
                return false
            }
            return true
        }
}

enum class SeansType {
    NORMAL, PREMIERE;
}