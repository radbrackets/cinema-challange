package com.highwaycinema.planner.service

import javax.naming.OperationNotSupportedException

class Room(
    val name: String,
    val maintenanceTime: Int
) {
    var seansList: MutableList<Seans> = mutableListOf()
    var availableTimeRanges: MutableList<IntRange> = initialRange
        private set

    fun addSeans(seans: Seans){
        if (findAvailableStartingTimeRangesForMovie(seans.movie).any{ seans.startTime in it}){
            seansList.add(seans)
            availableTimeRanges.splitBySeans(seans)
        } else throw NoAvailableTimeRangeException()
    }

    fun findAvailableTimeRangesForMovie(movie: Movie): List<IntRange> =
        availableTimeRanges
            .filter { (it.first + movie.durationTime + maintenanceTime) in it }

    fun findAvailableStartingTimeRangesForMovie(movie: Movie): List<IntRange> =
        findAvailableTimeRangesForMovie(movie).map {
            it - IntRange(start = (it.last - (movie.durationTime + maintenanceTime)), endInclusive = it.last)
        }

    private operator fun IntRange.minus(other: IntRange): IntRange{
        if(other.first < this.first || other.last != this.last ) throw OperationNotSupportedException("Removed range must be smaller and finish at last item on previous range")
        return IntRange(first, other.first - 1 )
    }

    private fun MutableList<IntRange>.splitBySeans(seans: Seans) =
        split(
            getRangeForSeans(seans) ?: throw NoAvailableTimeRangeException("No range found"),
            seans.startTime,
            seans.endTimeWithoutMaintanance + maintenanceTime
        )

    private fun getRangeForSeans(seans: Seans) =
        findAvailableTimeRangesForMovie(seans.movie).find { seans.startTime in it }

    private fun MutableList<IntRange>.split(rangeToSplit: IntRange, startTime: Int, endTime: Int){
        this.remove(rangeToSplit)
        this.add(IntRange(rangeToSplit.first, startTime))
        this.add(IntRange(endTime, rangeToSplit.last))
        this.sortBy { it.first }
    }

    private val initialRange
        get() = mutableListOf(IntRange(CinemaTime.OPENING_TIME, CinemaTime.CLOSING_TIME))
}