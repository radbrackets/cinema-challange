package com.radbrackets.cinemachallenge.domain

import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

internal class SeansSchedulerTest {

    private val scheduler = SeansScheduler(DumbMovieRepository(), DumbRoomRepository())

    @Test
    fun shouldReturnRoomUnavailable_whenRoomUnavailable() {
        // given
        val request = ScheduleRequest(
            "Forrest Gump", 3,
            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 0)), false
        )

        // when
        val response = scheduler.schedule(request)

        // then
        assertThat(response).isEqualTo(ResponseCode.ROOM_NOT_AVAILABLE)
    }

    @Test
    fun shouldCorrectlyAddSeans_whenRoomHasAllSlotsFree() {
        // given
        val request = ScheduleRequest(
            "Forrest Gump", 1,
            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 0)), false
        )

        // when
        val response = scheduler.schedule(request)

        // then
        assertThat(response).isEqualTo(ResponseCode.SCHEDULED)
    }

    @Test
    fun shouldCorrectlyAddSeans_whenSlotsNotOverlapping() {
        // given
        val date = LocalDate.now().plusDays(2)
        val request1 = ScheduleRequest(
            "The Godfather", 2, LocalDateTime.of(
                date,
                LocalTime.of(8, 0)
            ), false
        )
        val request2 = ScheduleRequest(
            "Avatar", 2, LocalDateTime.of(
                date,
                LocalTime.of(12, 0)
            ), false
        )

        // when
        scheduler.schedule(request1)
        val response = scheduler.schedule(request2)

        // then
        assertThat(response).isEqualTo(ResponseCode.SCHEDULED)
    }

    @Test
    fun shouldReturnWrongTimeslot_whenSeansAfterWorkingHours() {
        // given
        val request = ScheduleRequest(
            "The Godfather", 2,
            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(22, 0)), false
        )

        // then
        val response = scheduler.schedule(request)

        // then
        assertThat(response).isEqualTo(ResponseCode.WRONG_TIMESLOT)
    }

    @Test
    fun shouldReturnWrongTimeslot_whenPremiereInWrongSlot() {
        // given
        val premiereRequest = ScheduleRequest(
            "The Godfather", 2,
            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 0)), true
        )

        // then
        val response = scheduler.schedule(premiereRequest)

        // then
        assertThat(response).isEqualTo(ResponseCode.WRONG_TIMESLOT)
    }

    @Test
    fun shouldReturnScheduled_whenPremiereInCorrectSlot() {
        // given
        val date = LocalDate.now().plusDays(2)
        val time = LocalTime.of(17, 0)
        val premiereRequest = ScheduleRequest("The Godfather", 2, LocalDateTime.of(date, time), true)

        // then
        val response = scheduler.schedule(premiereRequest)

        // then
        assertThat(response).isEqualTo(ResponseCode.SCHEDULED)
    }

    @Test
    fun shouldReturnSeansIsOverlapping_whenSecondStartsDuringFirst() {
        // given
        val date = LocalDate.now().plusDays(2)
        val time = LocalTime.of(12, 0)
        val request1 = ScheduleRequest("The Godfather", 2, LocalDateTime.of(date, time), false)
        val request2 = ScheduleRequest(
            "Avatar", 2, LocalDateTime.of(date, time.plusMinutes(15)), false
        )

        // then
        scheduler.schedule(request1)
        val response = scheduler.schedule(request2)

        // then
        assertThat(response).isEqualTo(ResponseCode.SEANS_IS_OVERLAPPING)
    }

    @Test
    fun shouldReturnSeansIsOverlapping_whenSecondEndsDuringFirst() {
        // given
        val date = LocalDate.now().plusDays(2)
        val request1 = ScheduleRequest(
            "The Godfather", 2,
            LocalDateTime.of(date, LocalTime.of(12, 0)), false
        )
        val request2 = ScheduleRequest(
            "Avatar", 2, LocalDateTime.of(date, LocalTime.of(11, 30)), false
        )

        // then
        scheduler.schedule(request1)
        val response = scheduler.schedule(request2)

        // then
        assertThat(response).isEqualTo(ResponseCode.SEANS_IS_OVERLAPPING)
    }

    @Test
    fun shouldReturnSeansIsOverlapping_whenBothStartsAtTheSameTime() {
        // given
        val date = LocalDate.now().plusDays(2)
        val time = LocalTime.of(12, 0)
        val start = LocalDateTime.of(date, time)
        val request1 = ScheduleRequest("The Godfather", 2, start, false)
        val request2 = ScheduleRequest(
            "Avatar", 2, start, false
        )

        // then
        scheduler.schedule(request1)
        val response = scheduler.schedule(request2)

        // then
        assertThat(response).isEqualTo(ResponseCode.SEANS_IS_OVERLAPPING)
    }

    @Test
    fun shouldScheduleOnlyOneSeans_whenMultipleRequestsAtTheSameTime() {
        val request = ScheduleRequest(
            "Forrest Gump", 1,
            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 0)), false
        )
        val results = Collections.synchronizedList(mutableListOf<ResponseCode>())
        runBlocking { prepareCoroutineScope(request, results) }
        val resultMap = results.groupBy { k -> k }
        assertThat(resultMap.get(ResponseCode.SCHEDULED)?.size).isEqualTo(1)
        assertThat(resultMap.get(ResponseCode.SEANS_IS_OVERLAPPING)?.size).isEqualTo(24)
    }

    private suspend fun prepareCoroutineScope(request: ScheduleRequest, results: MutableList<ResponseCode>) {
        val scope = CoroutineScope(newFixedThreadPoolContext(4, "testingPool"))
        scope.launch {
            val coroutines = 1.rangeTo(5).map {
                launch {
                    for (i in 1..5) {
                        results.add(scheduler.schedule(request))
                    }
                }
            }

            coroutines.forEach { c ->
                c.join()
            }
        }.join()
    }
}


