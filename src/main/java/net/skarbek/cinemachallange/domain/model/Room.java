package net.skarbek.cinemachallange.domain.model;

public class Room {

    private final RoomId id;
    private final String label;
    private final CleaningOverhead cleaningOverhead;


    public Room(RoomId id, String label, CleaningOverhead cleaningOverhead) {
        this.id = id;
        this.label = label;
        this.cleaningOverhead = cleaningOverhead;
    }

    public RoomId getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public CleaningOverhead getCleaningOverhead() {
        return cleaningOverhead;
    }
}
