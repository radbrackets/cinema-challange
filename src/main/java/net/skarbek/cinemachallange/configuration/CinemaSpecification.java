package net.skarbek.cinemachallange.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@ConfigurationProperties(prefix = "cinema")
public class CinemaSpecification {

    private LocalTime openingTime;
    private LocalTime closingTime;

    public LocalTime getOpenTime() {
        return openingTime;
    }

    public LocalTime getCloseTime() {
        return closingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public CinemaSpecification withOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public CinemaSpecification withClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
        return this;
    }
}
