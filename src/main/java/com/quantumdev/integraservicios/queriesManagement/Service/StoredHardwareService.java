package com.quantumdev.integraservicios.queriesManagement.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quantumdev.integraservicios.database.model.Building;
import com.quantumdev.integraservicios.database.model.ScheduleEntry;
import com.quantumdev.integraservicios.database.model.StoredHardware;
import com.quantumdev.integraservicios.database.model.StoredHardwareId;
import com.quantumdev.integraservicios.database.model.Warehouse;
import com.quantumdev.integraservicios.database.model.WarehouseId;
import com.quantumdev.integraservicios.database.repository.BuildingRepository;
import com.quantumdev.integraservicios.database.repository.StoredHardwareRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class for managing stored hardware items.
 * This class provides methods to retrieve available items based on various criteria.
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class StoredHardwareService {

    /** Repository for accessing stored hardware data */
    private final StoredHardwareRepository storedHardwareRepository;
    /** Repository for accessing building data */
    private final BuildingRepository buildingRepository;

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

        // Determine the day of the week for the start date.
        String dayChar = ScheduleEntry.DAY_MAP.get(startDateTime.getDayOfWeek()).toString();

        // Get the list of non-reserved items within the specified date range.
        return this.storedHardwareRepository.getNonReserved(
                    nameLike,
                    type,
                    building,
                    dayChar,
                    startDate,
                    endDate,
                    pageable
                );
    }

    /**
     * Retrieves the details of a specific stored hardware item by its composite key.
     * @param building  The building number.
     * @param warehouse The warehouse number.
     * @param id        The ID of the stored hardware item.
     * @return          The stored hardware item matching the provided keys or null if not found.
     */
    public StoredHardware getDetails(Short building, Short warehouse, Short id) {
        // Retrieve the stored hardware item by its composite key.
        Building buildingEntity = buildingRepository.findById(building).orElse(null);
        if (buildingEntity == null)
            return null;

        WarehouseId warehouseId = WarehouseId.builder()
                                    .code(warehouse)
                                    .building(buildingEntity)
                                    .build();
        
        StoredHardwareId storedHardwareId = StoredHardwareId.builder()
                                                .code(id)
                                                .warehouse(Warehouse.builder().id(warehouseId).build())
                                                .build();

        return this.storedHardwareRepository
                .findById(storedHardwareId)
                .orElse(null);
    }
    
}
