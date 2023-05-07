package com.velocit.cinema
package movie

import room.ScreenType._

import org.joda.time.Duration

object MovieRepository {
  def getMovieCatalog: List[Movie] = List(
    Movie(MovieId("1"), "Super Mario Bros", Duration.standardMinutes(95), Screen3D),
    Movie(MovieId("2"), "Godfather", Duration.standardMinutes(210), Screen2D),
    Movie(MovieId("3"), "Lord of the Rings: Fellowship of the Ring", Duration.standardMinutes(180), Screen2D),
    Movie(MovieId("4"), "Harry Potter and Chamber of Secrets", Duration.standardMinutes(150), Screen2D),
    Movie(MovieId("5"), "Guardians of the Galaxy vol. 3", Duration.standardMinutes(136), Screen3D),
    Movie(MovieId("6"), "Dungeons&Dragons: Honour among thieves", Duration.standardMinutes(104), Screen3D),
  )
}
