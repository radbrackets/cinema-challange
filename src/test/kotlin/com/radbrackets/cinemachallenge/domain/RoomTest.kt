package com.radbrackets.cinemachallenge.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RoomTest {

    private val movies = mutableListOf<Movie>()
    private lateinit var tested: Room

    @BeforeEach
    fun setup() {
        movies.add(Movie("The Godfather", false, 175))
        movies.add(Movie("Forrest Gump", false, 142))
        tested = Room(1, true, 30)
    }

    @Test
    fun shouldReturnNotOverlapping_whenAreSlotsFree() {
        // given
        val seans = Seans.createSeans(
            movies[0], tested, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0)),
            false
        )

        // when
        val isOverlapping = tested.isSeansOverlapping(seans)

        // then
        assertThat(isOverlapping).isFalse
    }

    @Test
    fun shouldReturnNotOverlapping_whenSeansesNotOverlapping() {
        // given
        val seans = Seans.createSeans(
            movies[0], tested, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0)),
            false
        )
        val seans2 = Seans.createSeans(
            movies[1], tested, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(8, 0)),
            false
        )
        tested.seanses.add(seans)

        // when
        val isOverlapping = tested.isSeansOverlapping(seans2)

        // then
        assertThat(isOverlapping).isFalse
    }

    @Test
    fun shouldReturnOverlapping_whenSeansesStartsAtTheSameTime() {
        // given
        val seans = Seans.createSeans(
            movies[0], tested, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0)),
            false
        )
        val seans2 = Seans.createSeans(
            movies[1], tested, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0)),
            false
        )
        tested.seanses.add(seans)

        // when
        val isOverlapping = tested.isSeansOverlapping(seans2)

        // then
        assertThat(isOverlapping).isTrue
    }

    @Test
    fun shouldReturnOverlapping_whenSeansesOverlapping() {
        // given
        val seans = Seans.createSeans(
            movies[0], tested, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0)),
            false
        )
        val seans2 = Seans.createSeans(
            movies[1], tested, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13, 20)),
            false
        )
        tested.seanses.add(seans)

        // when
        val isOverlapping = tested.isSeansOverlapping(seans2)

        // then
        assertThat(isOverlapping).isTrue
    }

}