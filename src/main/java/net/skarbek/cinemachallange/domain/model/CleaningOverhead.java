package net.skarbek.cinemachallange.domain.model;

import org.springframework.util.Assert;

import java.time.Duration;

public class CleaningOverhead {

    private final  Duration duration;

    public CleaningOverhead(Duration duration) {
        Assert.notNull(duration, "duration cannot be null");
        Assert.isTrue(!duration.isNegative(), "duration cannot be negative");
        Assert.isTrue(!duration.isZero(), "duration cannot be 0");
        this.duration = duration;
    }

    public static CleaningOverhead ofMinutes(int minutes){
        return new CleaningOverhead(Duration.ofMinutes(minutes));
    }

    public static CleaningOverhead of(Duration duration){
        return new CleaningOverhead(duration);
    }

    public Duration getDuration() {
        return duration;
    }
}
