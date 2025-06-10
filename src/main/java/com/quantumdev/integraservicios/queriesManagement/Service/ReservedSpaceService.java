package com.quantumdev.integraservicios.queriesManagement.Service;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quantumdev.integraservicios.database.model.ReservedSpace;
import com.quantumdev.integraservicios.database.repository.ReservedSpaceRepository;

import lombok.RequiredArgsConstructor;

/**
 * ReservedSpaceService is a service that provides methods to query reserved spaces in the system.
 * It allows filtering based on various criteria such as email, name, type, capacity, building, and reservation dates.
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class ReservedSpaceService {

    private final ReservedSpaceRepository reservedSpaceRepository;

    /**
     * Retrieves a reserved space by its ID.
     * @param id The ID of the reserved space to retrieve.
     * @return ReservedHardware object representing the reserved space with the given ID, or null if not found.
     */
    public ReservedSpace getById(Long id) {
        return this.reservedSpaceRepository
                    .findById(id)
                    .orElse(null);
    }

    /**
     * Retrieves the history of reserved spaces based on the provided filters.
     * @param email     Email of the user requesting the history.
     * @param nameLike  Partial name of the space to search for (optional).
     * @param type      Type of the space (optional).
     * @param capacity  Minimum capacity of the space (optional).
     * @param building  Building number where the space was located (optional).
     * @param startDate Start date for the reservation period (optional).
     * @param endDate   End date for the reservation period (optional).
     * @param qSize     Number of reservations to retrieve per page.
     * @param qPage     Page number to retrieve.
     * @param orderBy   Field to order the results by (optional).
     * @param ascOrder  Flag to indicate if the results should be ordered in ascending order (assumed true).
     * @return          List of reserved spaces matching the filters.
     */
    public List<ReservedSpace> get(
        String email,
        String nameLike,
        String type,
        Short capacity,
        Short building,
        Instant startDate,
        Instant endDate,
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
        
        return this.reservedSpaceRepository.get(
                email,
                nameLike,
                type,
                capacity,
                building,
                startDate,
                endDate,
                pageable
            );
    }
    
}
