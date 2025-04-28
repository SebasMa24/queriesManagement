package com.quantumdev.integraservicios.database.model;

import java.time.DayOfWeek;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Space")
public class Space {

    @Id
    private SpaceId id;

    @ManyToOne(
        targetEntity = SpaceType.class,
        optional = false
    )
    @JoinColumn(
        name = "type_space",
        nullable = false
    )
    private SpaceType type;

    @ManyToOne(
        targetEntity = State.class,
        optional = false
    )
    @JoinColumn(
        name = "state_space",
        nullable = false
    )
    private State state;

    @NotEmpty
    @Column(
        name = "name_space",
        length = 64,
        nullable = false
    )
    private String name;

    @Column(
        name = "capacity_space",
        nullable = false
    )
    private Short capacity;

    @NotEmpty
    @Getter(AccessLevel.NONE)
    @Column(
        name = "schedule_space",
        length = 49,
        nullable = false
    )
    private String schedule;

    @Column(
        name = "desc_space",
        length = 64,
        nullable = true
    )
    private String description;

    /**
     * Converts the schedule string to a list of ScheduleEntry objects.
     * @return A list of ScheduleEntry objects representing the schedule.
     */
    public List<ScheduleEntry> getSchedule() {
        List<ScheduleEntry> entries = new ArrayList<>();

        // Split the schedule string in individual entries by the ',' character.
        String[] entriesStr = this.schedule.split(",");
        for (String entryStr : entriesStr)
            entries.add(ScheduleEntry.valueOf(entryStr));
    
        return entries;
    }

    /**
     * Adds a schedule entry to the schedule string.
     * @param entry The ScheduleEntry object to add.
     */
    public void addScheduleEntry(ScheduleEntry entry) {
        // Only one entry per day of the week is allowed.
        int index = this.schedule.indexOf(ScheduleEntry.DAY_MAP.get(entry.getDay()));
        if (index >= 0)
            throw new IllegalArgumentException("Schedule entry for " + entry.getDay() + " already exists.");

        this.schedule += "," + entry;
    }

    /**
     * Removes a schedule entry for a given day of the week in the schedule string.
     * @param day The DayOfWeek enum value representing the day to remove.
     */
    public void removeScheduleEntry(DayOfWeek day) {
        // Find the schedule entry for the given day and remove it.
        List<ScheduleEntry> entries = this.getSchedule();
        for (ScheduleEntry entry : entries)
            if (entry.getDay() == day) {
                entries.remove(entry);
                break;
            }

        // If the entry was not found, throw an exception.
        if (entries.size() == this.getSchedule().size())
            throw new IllegalArgumentException("Schedule entry for " + day + " not found.");

        // Rebuild the schedule string from the remaining entries.
        this.schedule = "";
        for (ScheduleEntry entry : entries)
            this.addScheduleEntry(entry);
    }

}
