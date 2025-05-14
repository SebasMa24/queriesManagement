package com.quantumdev.integraservicios.database.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.quantumdev.integraservicios.database.model.ReservedHardware;

/**
 * ReservedHardwareRepository is a Spring Data JPA repository interface for managing ReservedHardware entities.
 * It provides methods to query reserved hardware items based on various filters.
 * @author Nicolás Sabogal
 */
@Repository
public interface ReservedHardwareRepository extends JpaRepository<ReservedHardware, Long> {

    /**
     * Retrieves the history of reserved hardware items based on the provided filters.
     * @param email     Email of the user requesting the history.
     * @param nameLike  Partial name of the hardware item to search for (optional).
     * @param type      Type of the hardware item (optional).
     * @param building  Building number where the hardware was located (optional).
     * @param startDate Start date for the reservation period (optional).
     * @param endDate   End date for the reservation period (optional).
     * @param pageable  Pagination information.
     * @return          List of reserved hardware items matching the filters.
     */
    @Query(
        value = """
            SELECT rh.*
            FROM reservedHardware rh
            INNER JOIN storedhardware sh
                ON rh.building_reshw = sh.building_storedhw
                AND rh.warehouse_reshw = sh.warehouse_storedhw
                AND rh.storedhw_reshw = sh.code_storedhw
            INNER JOIN hardware hw
                ON sh.hardware_storedhw = hw.code_hardware
            WHERE
                (:email IS NULL OR rh.requester_reshw = :email)
                AND (:nameLike IS NULL OR hw.name_hardware LIKE CONCAT('%', :nameLike, '%'))
                AND (:type IS NULL OR hw.type_hardware = :type)
                AND (:building IS NULL OR sh.building_storedhw = :building)
                AND (CAST(:startDate AS TIMESTAMP) IS NULL OR rh.start_reshw >= CAST(:startDate AS TIMESTAMP))
                AND (CAST(:endDate AS TIMESTAMP) IS NULL OR rh.end_reshw <= CAST(:endDate AS TIMESTAMP));
        """,
        nativeQuery = true
    )
    List<ReservedHardware> get(
        String email,
        String nameLike,
        String type,
        Short building,
        Instant startDate,
        Instant endDate,
        Pageable pageable
    );

}
