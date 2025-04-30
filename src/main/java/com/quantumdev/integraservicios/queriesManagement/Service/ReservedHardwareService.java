package com.quantumdev.integraservicios.queriesManagement.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.quantumdev.integraservicios.database.model.ReservedHardware;
import com.quantumdev.integraservicios.database.repository.ReservedHardwareRepository;

/**
 * ReservedHardwareService is a service that provides methods to query reserved hardware in the system.
 * It allows filtering based on various criteria such as email, name, type, building, and reservation dates.
 * @author Nicolás Sabogal
 */
public class ReservedHardwareService {

    @Autowired
    private ReservedHardwareRepository reservedHardwareRepository;

    /**
     * Retrieves the history of reserved hardware items based on the provided filters.
     * @param email     Email of the user requesting the history.
     * @param nameLike  Partial name of the hardware item to search for (optional).
     * @param type      Type of the hardware item (optional).
     * @param building  Building number where the hardware was located (optional).
     * @param startDate Start date for the reservation period (optional).
     * @param endDate   End date for the reservation period (optional).
     * @param qSize     Number of reservations to retrieve per page.
     * @param qPage     Page number to retrieve.
     * @param orderBy   Field to order the results by (optional).
     * @param ascOrder  Flag to indicate if the results should be ordered in ascending order (assumed true).
     * @return          List of reserved hardware items matching the filters.
     */
    public List<ReservedHardware> get(
        String email,
        String nameLike,
        String type,
        Short building,
        Instant startDate,
        Instant endDate,
        Integer qSize,
        Integer qPage,
        String orderBy,
        Boolean ascOrder
    ) {
        // Validation.
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email is required.");

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
                pageable
            );
    }
    
}
