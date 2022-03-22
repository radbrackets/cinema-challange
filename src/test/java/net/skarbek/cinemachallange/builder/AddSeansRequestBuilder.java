package net.skarbek.cinemachallange.builder;

import net.skarbek.cinemachallange.SampleData;
import net.skarbek.cinemachallange.api.model.AddSeansRequest;
import net.skarbek.cinemachallange.domain.model.MovieId;
import net.skarbek.cinemachallange.domain.model.RoomId;
import net.skarbek.cinemachallange.domain.model.WeekOfYear;

import java.time.LocalTime;
import java.util.function.BiConsumer;

public class AddSeansRequestBuilder {

    private String movieId = SampleData.MOVIE_ID.stringValue();
    private String roomId = SampleData.ROOM_1_ID.stringValue();
    private LocalTime seansStartTime = LocalTime.of(10,0);
    private WeekOfYear weekOfYear = new WeekOfYear(2020, 11);

    BiConsumer<AddSeansRequest, WeekOfYear> consumer;

    public AddSeansRequestBuilder(BiConsumer<AddSeansRequest, WeekOfYear> consumer) {
        this.consumer = consumer;
    }

    public AddSeansRequestBuilder inWeekOfYear(WeekOfYear weekOfYear){
        this.weekOfYear = weekOfYear;
        return this;
    }

    public AddSeansRequestBuilder forMovie(MovieId movieId) {
        this.movieId = movieId.stringValue();
        return this;
    }

    public AddSeansRequestBuilder toRoomId(RoomId roomId) {
        this.roomId = roomId.stringValue();
        return this;
    }

    public AddSeansRequestBuilder withStartDate(LocalTime localTime) {
        this.seansStartTime = localTime;
        return this;
    }

    public Exception received(){
        AddSeansRequest addSeansRequest = new AddSeansRequest(movieId, roomId, seansStartTime);
        try {
            consumer.accept(addSeansRequest, weekOfYear);
            return null;
        }catch (RuntimeException e){
            return e;
        }
    }
}
