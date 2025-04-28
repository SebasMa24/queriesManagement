package com.quantumdev.integraservicios.database.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntry {

    /** Map for the DayOfWeek enum values to their corresponding characters. */
    public static final Map<DayOfWeek, Character> DAY_MAP = Map.of(
        DayOfWeek.MONDAY, 'M',
        DayOfWeek.TUESDAY, 'T',
        DayOfWeek.WEDNESDAY, 'W',
        DayOfWeek.THURSDAY, 'H',
        DayOfWeek.FRIDAY, 'F',
        DayOfWeek.SATURDAY, 'S',
        DayOfWeek.SUNDAY, 'U'
    );

    /** Map for the characters to their corresponding DayOfWeek enum values. */
    public static final Map<Character, DayOfWeek> CHAR_MAP = Map.of(
        'M', DayOfWeek.MONDAY,
        'T', DayOfWeek.TUESDAY,
        'W', DayOfWeek.WEDNESDAY,
        'H', DayOfWeek.THURSDAY,
        'F', DayOfWeek.FRIDAY,
        'S', DayOfWeek.SATURDAY,
        'U', DayOfWeek.SUNDAY
    );

    /**
     * Converts a string representation of a schedule entry to a ScheduleEntry object.
     * The format is DHH-HH, where D is the day of the week character,
     * HH is the start hour, and HH is the end hour.
     * The day of the week is represented by a single character:
     * M - Monday, T - Tuesday, W - Wednesday, H - Thursday,
     * F - Friday, S - Saturday, U - Sunday.
     * @param entry The string representation of the schedule entry.
     * @return A ScheduleEntry object representing the schedule entry.
     * @throws IllegalArgumentException if the entry string is invalid.
     */
    public static final ScheduleEntry valueOf(String entry) {
        // Convert the first character to a DayOfWeek enum value.
        char dayChar = entry.charAt(0);
        DayOfWeek day = CHAR_MAP.get(dayChar);
        if (day == null)
            throw new IllegalArgumentException("Invalid day character: " + dayChar);

        // Get the time range from the string.
        String[] timeRangeStr = entry.substring(1).split("-");
        if (timeRangeStr.length != 2)
            throw new IllegalArgumentException("Invalid time range: " + entry.substring(1));

        int startInt = Integer.parseInt(timeRangeStr[0]);
        int endInt = Integer.parseInt(timeRangeStr[1]);
        if (startInt < 0 || startInt > 23 || endInt < 0 || endInt > 23 || startInt >= endInt)
            throw new IllegalArgumentException("Invalid time range: " + entry.substring(1));
        
        // Convert the start and end times to LocalTime objects.
        LocalTime start = LocalTime.of(startInt, 0);
        LocalTime end = LocalTime.of(endInt, 0);

        return new ScheduleEntry(day, start, end);
    }


    private DayOfWeek day;
    private LocalTime start;
    private LocalTime end;

    /**
     * Converts the ScheduleEntry object to a string representation.
     * The format is DHH-HH, where D is the day of the week character,
     * HH is the start hour, and HH is the end hour.
     * The day of the week is represented by a single character:
     * M - Monday, T - Tuesday, W - Wednesday, H - Thursday,
     * F - Friday, S - Saturday, U - Sunday.
     */
    public String toString() {
        // Convert the DayOfWeek enum value to a character.
        char dayChar = DAY_MAP.get(this.getDay());

        // Create the entry string with the format DHH-HH.
        String entryStr = dayChar + String.format("%02d", this.getStart().getHour()) + "-" +
            String.format("%02d", this.getEnd().getHour());
        
        return entryStr;
    }

}
