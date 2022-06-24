package com.radbrackets.cinemachallenge.domain;

import lombok.Data;

import java.time.Duration;

@Data
class Movie {
    String movieId;
    boolean isPremiere;
    boolean isIn3d;
    Duration movieDuration;
}
