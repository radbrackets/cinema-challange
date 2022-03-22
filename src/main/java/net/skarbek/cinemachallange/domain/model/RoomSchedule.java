package net.skarbek.cinemachallange.domain.model;

import net.skarbek.cinemachallange.domain.command.AddSeanceCommand;
import net.skarbek.cinemachallange.domain.exception.DomainException;

import java.util.HashSet;
import java.util.Set;

public class RoomSchedule extends AggregateRoot {

    private final WeekOfYear weekOfYear;
    private final Room room;
    private final Set<Seance> seances = new HashSet<>();

    public RoomSchedule(WeekOfYear weekOfYear, Room room) {
        this.weekOfYear = weekOfYear;
        this.room = room;
    }

    public void addSeance(AddSeanceCommand cmd) {
        Seance newSeance = new Seance(
                cmd.getMovie(),
                cmd.getRoom().getCleaningOverhead(),
                cmd.getSeansStartTime(),
                cmd.getCinemaSpecification());

        seances.forEach(alreadyScheduledSeance -> {
            if (alreadyScheduledSeance.overlappsWith(newSeance)) {
                throw new DomainException("Seance overlapps with exisisting seanses ");
            }
        });
        seances.add(newSeance);
    }

    public WeekOfYear getWeekOfYear() {
        return weekOfYear;
    }

    public Room getRoom() {
        return room;
    }

    public Set<Seance> getSeances() {
        return this.seances;
    }
}
