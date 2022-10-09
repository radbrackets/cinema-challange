package com.radbrackets.cinemachallenge.domain

import java.util.*

data class Room(
    val roomId: Int, val isAvailable: Boolean,
    val breakDurationInMinutes: Long, val seanses: MutableList<Seans> = Collections.synchronizedList(mutableListOf())
) {

    fun isSeansOverlapping(seans: Seans): Boolean {
        return seanses
            .any { seans.start >= it.start && seans.start <= it.end  || seans.end >= it.start && seans.end <= it.end }
    }
}