package com.quantumdev.integraservicios.database.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantumdev.integraservicios.database.model.Space;
import com.quantumdev.integraservicios.database.model.SpaceId;

/**
 * Repository interface for managing Space entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 * It allows searching for spaces based on various filters and checking availability within a specified period.
 * @author Nicolás Sabogal
 */
@Repository
public interface SpaceRepository extends JpaRepository<Space, SpaceId> {
    
    /**
     * Method to retrieve a list of spaces based on the given filters.
     * @param nameLike The name of the space to search for (optional).
     * @param type     The type of the space to search for (optional).
     * @param capacity The minimum capacity of the space (optional).
     * @param building The building number to search for (optional).
     * @param pageable The pagination information.
     * @return         A list of spaces that match the given filters.
     */
    @Query(
        value = """
            SELECT s.*
            FROM Space s
            WHERE
                (:nameLike IS NULL OR s.name_space LIKE CONCAT('%', :nameLike, '%'))
                AND (:type IS NULL OR s.type_space = :type)
                AND (:capacity IS NULL OR s.capacity_space >= :capacity)
                AND (:building IS NULL OR s.building_space = :building)
            """,
        nativeQuery = true
    )
    public List<Space> get(
        String nameLike,
        String type,
        Short capacity,
        Short building,
        Pageable pageable
    );

    /**
     * Method to retrieve a list of available spaces based on the given filters in the specified period.
     * @param nameLike  The name of the space to search for (optional).
     * @param type      The type of the space to search for (optional).
     * @param capacity  The minimum capacity of the space (optional).
     * @param building  The building number to search for (optional).
     * @param startDate The start date for the reservation period.
     * @param endDate   The end date for the reservation period.
     * @param pageable  The pagination information.
     * @return          A list of available spaces that match the given filters.
     */
    @Query(
        value = """
            WITH rs AS (
                SELECT
                    r.building_resspace,
                    r.space_resspace
                FROM ReservedSpace r
                WHERE
                    r.start_resspace < CAST(:endDate AS TIMESTAMP)
                    AND r.end_resspace > CAST(:startDate AS TIMESTAMP)
            )
            SELECT sp.*
            FROM Space sp
            LEFT OUTER JOIN rs
                ON sp.building_space = rs.building_resspace
                AND sp.code_space = rs.space_resspace
            WHERE
                rs.space_resspace IS NULL
                AND (:nameLike IS NULL
                    OR sp.name_space LIKE CONCAT('%', :nameLike, '%')
                )
                AND (:type IS NULL
                    OR sp.type_space = :type
                )
                AND (:capacity IS NULL
                    OR sp.capacity_space >= :capacity
                )
                AND (:building IS NULL
                    OR sp.building_space = :building
                )
                AND (:dayChar IS NULL
                    OR sp.schedule_space LIKE CONCAT('%', :dayChar, '%')
                    AND CAST(SUBSTRING(sp.shcedule_space, POSITION(:dayChar IN sp.schedule_space) + 1, 2) AS INTEGER) <= EXTRACT(HOUR FROM CAST(:startDate AS TIMESTAMP))
                    AND CAST(SUBSTRING(sp.schedule_space, POSITION(:dayChar IN sp.schedule_space) + 4, 2) AS INTEGER) >= EXTRACT(HOUR FROM CAST(:endDate AS TIMESTAMP))
                )
        """,
        nativeQuery = true
    )
    public List<Space> getNonReserved(
        String nameLike,
        String type,
        Short capacity,
        Short building,
        String dayChar,
        Instant startDate,
        Instant endDate,
        Pageable pageable
    );

}
