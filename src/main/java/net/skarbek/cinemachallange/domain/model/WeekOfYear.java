package net.skarbek.cinemachallange.domain.model;

import org.springframework.util.Assert;

import java.util.Objects;

public class WeekOfYear {

    int year;
    int weekOfYear;

    public WeekOfYear(int year, int weekOfYear) {
        Assert.isTrue(weekOfYear <= 53, "week number exceed year week count");
        this.year = year;
        this.weekOfYear = weekOfYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeekOfYear that = (WeekOfYear) o;
        return year == that.year && weekOfYear == that.weekOfYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, weekOfYear);
    }


}
