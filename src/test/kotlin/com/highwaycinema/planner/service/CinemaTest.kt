package com.highwaycinema.planner.service

import com.highwaycinema.planner.service.CinemaTime.CLOSING_TIME
import com.highwaycinema.planner.service.CinemaTime.OPENING_TIME
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CinemaTest {
    private val blueRoom = Room("Blue", 15)
    private val redRoom = Room("Red", 10)
    private val vipRoom = Room("VIP", 30)

    private val cinema: Cinema = Cinema(listOf(blueRoom, redRoom, vipRoom))

    @Test
    fun should_find_all_rooms(){
        //GIVEN
        val movie = Movie("Example Movie", 280)

        //WHEN
        val availableRooms = cinema.findAvailableRoomsForMovie(movie)

        //THEN
        assertThat(availableRooms).containsExactlyInAnyOrder(blueRoom, redRoom, vipRoom)
    }

    @Test
    fun should_find_only_one_room(){
        //GIVEN
        val longDurationTime: Int = CLOSING_TIME - OPENING_TIME - 10
        val movie = Movie("Veeery looong Movie", longDurationTime)

        //WHEN
        val availableRooms = cinema.findAvailableRoomsForMovie(movie)

        //THEN
        assertThat(availableRooms).containsOnly(redRoom)


    }



}