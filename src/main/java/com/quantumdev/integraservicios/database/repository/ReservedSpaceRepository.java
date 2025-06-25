package com.quantumdev.integraservicios.database.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantumdev.integraservicios.database.model.ReservedSpace;

/**
 * ReservedSpaceRepository is a Spring Data JPA repository interface for managing ReservedSpace entities.
 * It provides methods to query reserved spaces based on various filters.
 * @author Nicolás Sabogal
 */
@Repository
public interface ReservedSpaceRepository extends JpaRepository<ReservedSpace, Long> {

    /**
     * Retrieves the history of reserved spaces based on the provided filters.
     * @param email        Email of the user requesting the history.
     * @param nameLike     Partial name of the space to search for (optional).
     * @param type         Type of the space (optional).
     * @param capacity     Minimum capacity of the space (optional).
     * @param building     Building number where the space was located (optional).
     * @param startDate    Start date for the reservation period (optional).
     * @param endDate      End date for the reservation period (optional).
     * @param isHandedOver Flag to indicate if the space has been handed over (optional, show both if not provided).
     * @param isReturned   Flag to indicate if the space has been returned (optional, show both if not provided).
     * @param pageable     Pagination information.
     * @return             List of reserved spaces matching the filters.
     */
    @Query(
        value = """
        SELECT rs.*
        FROM ReservedSpace rs
        JOIN Space sp
            ON rs.building_resspace = sp.building_space
            AND rs.space_resspace = sp.code_space
        WHERE
            (:email IS NULL OR rs.requester_resspace = :email)
            AND (:nameLike IS NULL OR sp.name_space LIKE '%' || :nameLike || '%')
            AND (:type IS NULL OR sp.type_space = :type)
            AND (:capacity IS NULL OR sp.capacity_space >= :capacity)
            AND (:building IS NULL OR rs.building_resspace = :building)
            AND (CAST(:startDate AS TIMESTAMP) IS NULL OR rs.start_resspace >= CAST(:startDate AS TIMESTAMP))
            AND (CAST(:endDate AS TIMESTAMP) IS NULL OR rs.end_resspace <= CAST(:endDate AS TIMESTAMP))
            AND (:isHandedOver IS NULL
                OR (:isHandedOver = TRUE AND rs.handover_resspace IS NOT NULL)
                OR (:isHandedOver = FALSE AND rs.handover_resspace IS NULL)
            )
            AND (:isReturned IS NULL
                OR (:isReturned = TRUE AND rs.return_resspace IS NOT NULL)
                OR (:isReturned = FALSE AND rs.return_resspace IS NULL)
            )
        """,
        nativeQuery = true
    )
    List<ReservedSpace> get(
        String email,
        String nameLike,
        String type,
        Short capacity,
        Short building,
        Instant startDate,
        Instant endDate,
        Boolean isHandedOver,
        Boolean isReturned,
        Pageable pageable
    );
    
}
