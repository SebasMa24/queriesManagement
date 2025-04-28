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
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "Hardware",
    uniqueConstraints = @UniqueConstraint(columnNames = "code_hardware")
)
public class Hardware {

    @Id
    @Column(
        name = "code_hardware",
        nullable = false
    )
    private Long code;

    @ManyToOne(
        targetEntity = HardwareType.class,
        optional = false
    )
    @JoinColumn(
        name = "type_hardware",
        nullable = false
    )
    private HardwareType type;
    
    @NotEmpty
    @Column(
        name = "name_hardware",
        length = 32,
        nullable = false
    )
    private String name;

    @NotEmpty
    @Getter(AccessLevel.NONE)
    @Column(
        name = "schedule_hardware",
        length = 49,
        nullable = false
    )
    private String schedule;

    @Column(
        name = "desc_hardware",
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
