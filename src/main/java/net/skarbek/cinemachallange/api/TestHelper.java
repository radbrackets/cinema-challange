package net.skarbek.cinemachallange.api;


import net.skarbek.cinemachallange.api.model.ActionResult;
import net.skarbek.cinemachallange.domain.model.*;
import net.skarbek.cinemachallange.infrastructure.fake.FakeMovieClient;
import net.skarbek.cinemachallange.infrastructure.fake.FakeRoomClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;


//TODO temporary helper for development purpose
@RestController
@RequestMapping("/helper/")
public class TestHelper {

    @Autowired
    FakeRoomClient fakeRoomClient;
    @Autowired
    FakeMovieClient fakeMovieClient;

    @PostMapping("populate-test-data")
    public ActionResult createTestData(){
        addRooms();
        addMovies();
        return ActionResult.success();
    }

    private void addMovies() {
        fakeMovieClient.addMovie(new Movie(new MovieId("m1"), "titanic", Duration.ofMinutes(100)));
        fakeMovieClient.addMovie(new Movie(new MovieId("m2"), "shrek", Duration.ofMinutes(120)));
    }

    private void addRooms() {
        fakeRoomClient.addRoom(new Room(new RoomId("r1"), "room-1", CleaningOverhead.ofMinutes(10)));
        fakeRoomClient.addRoom(new Room(new RoomId("r2"), "room-2", CleaningOverhead.ofMinutes(20)));
    }
}
