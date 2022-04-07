package com.highwaycinema.planner.service

class Seans(val startTime: Int, val movie: Movie) {
    val durationTime: Int by movie::durationTime
    val title: String by movie::title

    val endTimeWithoutMaintanance: Int
        get() = startTime + durationTime
}