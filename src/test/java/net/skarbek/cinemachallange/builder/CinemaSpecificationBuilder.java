package net.skarbek.cinemachallange.builder;

import net.skarbek.cinemachallange.configuration.CinemaSpecification;

import java.time.LocalTime;

public class CinemaSpecificationBuilder {

    private LocalTime openTime = LocalTime.of(8, 0);
    private LocalTime closeTime = LocalTime.of(22, 0);

    public static CinemaSpecificationBuilder sampleCinemaSpecification(){
        return new CinemaSpecificationBuilder();
    }

    public CinemaSpecificationBuilder withOpenTime(LocalTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public CinemaSpecificationBuilder withCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public CinemaSpecification build(){
        CinemaSpecification cinemaSpecification = new CinemaSpecification();
        cinemaSpecification.withClosingTime(closeTime);
        cinemaSpecification.withOpeningTime(openTime);
        return cinemaSpecification;

    }
}
