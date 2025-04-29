package com.quantumdev.integraservicios.queriesManagement.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quantumdev.integraservicios.database.model.ScheduleEntry;
import com.quantumdev.integraservicios.database.model.StoredHardware;
import com.quantumdev.integraservicios.database.repository.StoredHardwareRepository;

/**
 * Service class for managing stored hardware items.
 * This class provides methods to retrieve available items based on various criteria.
 * @author Nicolás Sabogal
 */
@Service
public class StoredHardwareService {

    @Autowired
    private StoredHardwareRepository storedHardwareRepository;

    /**
     * Retrieves a list of stored hardware items based on the provided criteria.
     * @param nameLike   The name pattern to search for (optional).
     * @param type       The type of hardware (optional).
     * @param building   The building number (optional).
     * @param startDate  The start date for availability check (optional).
     * @param endDate    The end date for availability check (optional).
     * @param getAll     Flag to indicate whether to retrieve all items or only available ones.
     * @param qSize      The size of the page to retrieve.
     * @param qPage      The page number to retrieve.
     * @param orderBy    The field to order the results by (optional).
     * @param ascOrder  Flag to indicate whether to sort in ascending order (optional).
     * @return           A list of stored hardware items matching the criteria.
     */
    public List<StoredHardware> get(
        String nameLike,
        String type,
        Short building,
        Instant startDate,
        Instant endDate,
        Boolean getAll,
        Integer qSize,
        Integer qPage,
        String orderBy,
        Boolean ascOrder
    ) {
        // Creating a Pageable object for pagination and sorting.
        Pageable pageable;
        if (orderBy == null || orderBy.isEmpty())
            pageable = PageRequest.of(qPage, qSize);

        else {
            Sort sort = Sort.by(orderBy);
            if (ascOrder == null || ascOrder)
                sort = sort.ascending();
            else
                sort = sort.descending();
            
            pageable = PageRequest.of(qPage, qSize, sort);
        }
        
        // If getAll is true, return all items without filtering by date.
        if (getAll != null && getAll)
            return this.storedHardwareRepository.get(
                        nameLike,
                        type,
                        building,
                        pageable
                    );

        // Otherwise, return only available items based the specified date range.
        if (startDate == null || endDate == null)
            throw new IllegalArgumentException("Start and end dates must be provided for availability check.");

        // Reservations can only be made for a single day.
        LocalDateTime startDateTime = LocalDateTime.ofInstant(startDate, ZoneId.of("UTC"));
        LocalDateTime endDateTime = LocalDateTime.ofInstant(endDate, ZoneId.of("UTC"));
        if (startDateTime.getDayOfYear() != endDateTime.getDayOfYear())
            throw new IllegalArgumentException("Reservations can only be made for a single day.");

        // Get the list of non-reserved items within the specified date range.
        List<StoredHardware> items = this.storedHardwareRepository.getNonReserved(
                    nameLike,
                    type,
                    building,
                    startDate,
                    endDate,
                    pageable
                );
        
        // Filter out items whose schedule entries are not contained in the specified date range.
        items.removeIf(item -> {
            boolean remove = true;
            // For each item, check if any of its schedule entries are contained in the specified date range.
            for (ScheduleEntry entry : item.getHardware().getSchedule()) {
                if (entry.getDay() != startDateTime.getDayOfWeek())
                    continue;
                if (    entry.getStart().compareTo(startDateTime.toLocalTime()) <= 0
                     && entry.getEnd().compareTo(endDateTime.toLocalTime()) >= 0
                   ) {
                    remove = false; // Found a cointained entry, do not remove the item.
                    break;
                }
            }
            return remove; // Remove is true if no contained entry was found.
        });

        return items;
    }
    
}
