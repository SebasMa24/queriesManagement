package com.quantumdev.integraservicios.database.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantumdev.integraservicios.database.model.StoredHardware;
import com.quantumdev.integraservicios.database.model.StoredHardwareId;

/**
 * Repository interface for managing StoredHardware entities.
 * This interface extends JpaRepository to provide CRUD operations and custom queries.
 * It allows searching for stored hardware based on various filters and checking availability within a specified period.
 * @author Nicolás Sabogal
 */
@Repository
public interface StoredHardwareRepository extends JpaRepository<StoredHardware, StoredHardwareId> {
    
    /** 
     * Method to retrieve a list of StoredHardware based on the given filters.
     * @param nameLike The name of the hardware to search for (optional).
     * @param type     The type of the hardware to search for (optional).
     * @param building The building number to search for (optional).
     * @param pageable The pagination information.
     * @return         A list of StoredHardware that matches the given filters.
     */
    @Query(
        value = """
        SELECT sh.*
        FROM StoredHardware sh
        INNER JOIN Hardware h ON h.code_hardware = sh.hardware_storedhw
        WHERE
            (:nameLike IS NULL OR h.name_hardware LIKE '%' || :nameLike || '%')
            AND (:type IS NULL OR h.type_hardware = :type)
            AND (:building IS NULL OR sh.building_storedhw = :building)
        """,
        nativeQuery = true
    )
    public List<StoredHardware> get(
        String nameLike,
        String type,
        Short building,
        Pageable pageable
    );

    /**
     * Method to retrieve a list of StoredHardware based on the given filters in the specified period.
     * @param nameLike  The name of the hardware to search for (optional).
     * @param type      The type of the hardware to search for (optional).
     * @param building  The building number to search for (optional).
     * @param startDate The start date for the reservation period.
     * @param endDate   The end date for the reservation period.
     * @param pageable  The pagination information.
     * @return          A list of StoredHardware that is available for reservation within the given period.
     */
    @Query(
        value = """
        WITH rsh AS (
            SELECT
                rh.building_reshw,
                rh.warehouse_reshw,
                rh.storedhw_reshw
            FROM ReservedHardware rh
            WHERE
                 rh.start_reshw < :startDate
                AND rh.end_reshw > :endDate
        )
        SELECT sh.*
        FROM StoredHardware sh
        INNER JOIN Hardware h ON h.code_hardware = sh.hardware_storedhw
        LEFT OUTER JOIN rsh ON
            sh.building_storedhw = rsh.building_reshw
            AND sh.warehouse_storedhw = rsh.warehouse_reshw
            AND sh.code_storedhw = rsh.storedhw_reshw
        WHERE
            sh.building_storedhw IS NULL
            AND (:nameLike IS NULL OR h.name_hardware LIKE '%' || :nameLike || '%')
            AND (:type IS NULL OR h.type_hardware = :type)
            AND (:building IS NULL OR sh.building_storedhw = :building)
        """,
        nativeQuery = true
    )
    public List<StoredHardware> getNonReserved(
        String nameLike,
        String type,
        Short building,
        Instant startDate,
        Instant endDate,
        Pageable pageable
    );
    
}
