package com.highwaycinema.planner.service

object CinemaTime {
    private const val OPENING_HOUR: Int = 8
    private const val CLOSING_HOUR: Int = 22
    private const val PREMIERE_STARTING_HOUR = 17
    private const val PREMIERE_ENDING_HOUR = 21

    const val OPENING_TIME: Int = OPENING_HOUR * 60
    const val CLOSING_TIME: Int = CLOSING_HOUR * 60

    const val PREMIERE_STARTING_TIME = PREMIERE_STARTING_HOUR * 60
    const val PREMIERE_ENDING_TIME = PREMIERE_ENDING_HOUR * 60


}