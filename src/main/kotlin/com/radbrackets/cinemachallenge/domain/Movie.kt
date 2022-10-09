package com.radbrackets.cinemachallenge.domain

data class Movie(
    val title: String, val is3d: Boolean = false, val durationInMinutes: Long
)