package com.highwaycinema.planner.service

import com.highwaycinema.planner.service.CinemaTime.CLOSING_TIME
import com.highwaycinema.planner.service.CinemaTime.OPENING_TIME
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RoomTest {

    lateinit var room: Room

    @BeforeEach
    fun setup(){
        room = Room("TestRoom", 10)
    }

    @Test
    fun seans_is_added(){
        //GIVEN
        val movie = Movie("One Day In Alaska", 150)

        //WHEN
        room.addSeans(Seans(10*60, movie))

        //THEN
        assertThat(room.seansList.map { it.movie }).containsOnly(movie)
    }

    @Test
    fun should_return_whole_day_for_movie(){
        //GIVEN
        val movie = Movie("One Day In Alaska", 150)

        //WHEN
        val actualRanges = room.findAvailableTimeRangesForMovie(movie)

        //THEN
        with(SoftAssertions()){
            assertThat(actualRanges).hasSize(1)

            val expectedRange = IntRange(
                start = OPENING_TIME,
                endInclusive = CLOSING_TIME
            )
            assertThat(actualRanges.first()).isEqualTo(expectedRange)
            assertAll()
        }
    }

    @Test
    fun should_return_two_ranges(){
        //SETUP
        val seans = Seans(
            OPENING_TIME + 8*60,
            Movie("I've been there already", 80)
        )
        room.addSeans(seans)

        //GIVEN
        val movieToAdd = Movie("Some another movie", 100)

        //WHEN
        val actualRanges = room.findAvailableTimeRangesForMovie(movieToAdd)

        //THEN
        with(SoftAssertions()){
            assertThat(actualRanges).hasSize(2)
            assertThat(actualRanges[0]).isEqualTo(
                IntRange(OPENING_TIME, seans.startTime)
            )
            assertThat(actualRanges[1]).isEqualTo(
                IntRange(
                    seans.endTimeWithoutMaintanance + room.maintenanceTime,
                    CLOSING_TIME
                )
            )
            assertAll()
        }
    }

    @Test
    fun should_return_only_one_range(){
        //SETUP
        val earlyBirdSeans = Seans(
            9*60,
            Movie("Some early birds movie but not at the opening", 120)
        )
        room.addSeans(earlyBirdSeans)

        //GIVEN
        val movie = Movie("Some movie to be viewed later", 60)

        //WHEN
        val actualRanges = room.findAvailableTimeRangesForMovie(movie)

        //THEN
        with(SoftAssertions()){
            assertThat(actualRanges).hasSize(1)
            assertThat(actualRanges.first()).isEqualTo(
                IntRange(
                    earlyBirdSeans.startTime + earlyBirdSeans.durationTime + room.maintenanceTime,
                    CLOSING_TIME
                )
            )
            assertAll()
        }

    }
}