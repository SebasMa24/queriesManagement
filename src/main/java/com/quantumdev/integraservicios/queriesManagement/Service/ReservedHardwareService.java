package com.quantumdev.integraservicios.queriesManagement.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quantumdev.integraservicios.database.model.ReservedHardware;
import com.quantumdev.integraservicios.database.repository.ReservedHardwareRepository;

import lombok.RequiredArgsConstructor;

/**
 * ReservedHardwareService is a service that provides methods to query reserved hardware in the system.
 * It allows filtering based on various criteria such as email, name, type, building, and reservation dates.
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class ReservedHardwareService {

    private final ReservedHardwareRepository reservedHardwareRepository;

    /**
     * Retrieves a reserved hardware item by its ID.
     * @param id ID of the reserved hardware item.
     * @return ReservedHardware object if found, null otherwise.
     */
    public ReservedHardware getById(Long id) {
        return this.reservedHardwareRepository.findById(id).orElse(null);
    }
    
    /**
     * Retrieves the history of reserved hardware items based on the provided filters.
     * @param email        Email of the user requesting the history.
     * @param nameLike     Partial name of the hardware item to search for (optional).
     * @param type         Type of the hardware item (optional).
     * @param building     Building number where the hardware was located (optional).
     * @param startDate    Start date for the reservation period (optional).
     * @param endDate      End date for the reservation period (optional).
     * @param isHandedOver Flag to indicate if the hardware has been handed over (optional, show both if not provided).
     * @param getReserved  Flag to indicate if the hardware has been reserved (optional, show both if not provided).
     * @param qSize        Number of reservations to retrieve per page.
     * @param qPage        Page number to retrieve.
     * @param orderBy      Field to order the results by (optional).
     * @param ascOrder     Flag to indicate if the results should be ordered in ascending order (assumed true).
     * @return             List of reserved hardware items matching the filters.
     */
    public List<ReservedHardware> get(
        String email,
        String nameLike,
        String type,
        Short building,
        Instant startDate,
        Instant endDate,
        Boolean isHandedOver,
        Boolean isReturned,
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

        return this.reservedHardwareRepository.get(
                email,
                nameLike,
                type,
                building,
                startDate,
                endDate,
                isHandedOver,
                isReturned,
                pageable
            );
    }

}
