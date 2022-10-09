package com.radbrackets.cinemachallenge.domain

import java.time.LocalDateTime

data class ScheduleRequest(val movieId: String, val roomId: Int, val startTime: LocalDateTime, val isPremiere: Boolean)