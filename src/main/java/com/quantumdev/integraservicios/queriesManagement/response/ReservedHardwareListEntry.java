package com.quantumdev.integraservicios.queriesManagement.response;

import java.time.LocalDate;
import java.time.ZoneId;

import com.quantumdev.integraservicios.database.model.Building;
import com.quantumdev.integraservicios.database.model.Hardware;
import com.quantumdev.integraservicios.database.model.HardwareType;
import com.quantumdev.integraservicios.database.model.ReservedHardware;
import com.quantumdev.integraservicios.database.model.StoredHardware;
import com.quantumdev.integraservicios.database.model.Warehouse;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an entry in the reserved hardware list.
 * This class is used to map the response from the database to a Java object.
 * It contains fields for building, warehouse, type, name, and reservation date of the hardware.
 * The @Data annotation from Lombok generates getters, setters, equals, hashCode,
 * and toString methods automatically.
 * @author Nicolás Sabogal
 */
@Data
@Builder
public class ReservedHardwareListEntry {

    // Primary Key
    private Long code_reshw;
    
    // Display fields
    private String name_building;
    private Short code_warehouse;
    private String name_hardware;
    private String type_hardware;
    private LocalDate day_reshw;

    /**
     * Converts a ReservedHardware entity to a ReservedHardwareListEntry response object.
     * @param reservedHardware the ReservedHardware entity to convert
     * @return                 the converted ReservedHardwareListEntry object
     */
    public static final ReservedHardwareListEntry from(ReservedHardware reservedHardware) {
        StoredHardware storedHardware = reservedHardware.getStoredHardware();
        Warehouse warehouse = storedHardware.getId().getWarehouse();
        Building building = warehouse.getId().getBuilding();
        Hardware hardware = storedHardware.getHardware();
        HardwareType hardwareType = hardware.getType();
        return ReservedHardwareListEntry.builder()
                .code_reshw(reservedHardware.getCode())
                .name_building(building.getName())
                .code_warehouse(warehouse.getId().getCode())
                .name_hardware(hardware.getName())
                .type_hardware(hardwareType.getName())
                .day_reshw(
                    reservedHardware
                        .getStartDate()
                        .atZone(ZoneId.of("UTC"))
                        .toLocalDate()
                )
                .build();
    }
    
}
