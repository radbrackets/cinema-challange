package net.skarbek.cinemachallange.builder;

import net.skarbek.cinemachallange.SampleData;
import net.skarbek.cinemachallange.domain.model.CleaningOverhead;
import net.skarbek.cinemachallange.domain.model.Room;
import net.skarbek.cinemachallange.domain.model.RoomId;
import net.skarbek.cinemachallange.infrastructure.fake.FakeRoomClient;

import static java.time.Duration.ofMinutes;

public class RoomBuilder {

    private FakeRoomClient fakeRoomClient;
    private RoomId id = SampleData.ROOM_1_ID;
    private String label = "room-1";
    private CleaningOverhead cleaningOverhead = new CleaningOverhead(ofMinutes(20));

    private RoomBuilder(FakeRoomClient fakeRoomClient) {
        this.fakeRoomClient = fakeRoomClient;
    }

    public Room build() {
        return new Room(id, label, cleaningOverhead);
    }

    public void inExternalSource(){
        this.fakeRoomClient.addRoom(this.build());
    }

    public static RoomBuilder sampleRoom() {
        return new RoomBuilder(null);
    }

    public static RoomBuilder sampleRoom(FakeRoomClient fakeRoomClient) {
        return new RoomBuilder(fakeRoomClient);
    }

    public RoomBuilder withId(RoomId id) {
        this.id = id;
        return this;
    }

    public RoomBuilder withLabel(String label) {
        this.label = label;
        return this;
    }

    public RoomBuilder withCleaningOverhead(CleaningOverhead cleaningOverhead) {
        this.cleaningOverhead = cleaningOverhead;
        return this;
    }
}
