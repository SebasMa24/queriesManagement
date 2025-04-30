package com.quantumdev.integraservicios.database.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quantumdev.integraservicios.database.model.ReservedSpace;

/**
 * ReservedSpaceRepository is a Spring Data JPA repository interface for managing ReservedSpace entities.
 * It provides methods to query reserved spaces based on various filters.
 * @author Nicolás Sabogal
 */
public interface ReservedSpaceRepository extends JpaRepository<ReservedSpace, Long> {

    /**
     * Retrieves the history of reserved spaces based on the provided filters.
     * @param email     Email of the user requesting the history.
     * @param nameLike  Partial name of the space to search for (optional).
     * @param type      Type of the space (optional).
     * @param capacity  Minimum capacity of the space (optional).
     * @param building  Building number where the space was located (optional).
     * @param startDate Start date for the reservation period (optional).
     * @param endDate   End date for the reservation period (optional).
     * @param pageable  Pagination information.
     * @return          List of reserved spaces matching the filters.
     */
    @Query(
        value = """
        SELECT rs.*
        FROM ReservedSpace rs
        JOIN Space s ON
            rs.building_resspace = s.building_space
            AND rs.space_resspace = s.code_space
        WHERE
            rs.requester_resspace = :email
            AND (:nameLike IS NULL OR s.name_space LIKE '%' || :nameLike || '%')
            AND (:type IS NULL OR s.type_space = :type)
            AND (:capacity IS NULL OR s.capacity_space >= :capacity)
            AND (:building IS NULL OR rs.building_resspace = :building)
            AND (:startDate IS NULL OR rs.start_resspace >= :startDate)
            AND (:endDate IS NULL OR rs.end_resspace <= :endDate)
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
        Pageable pageable
    );
    
}
