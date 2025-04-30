package com.quantumdev.integraservicios.queriesManagement.response;

import com.quantumdev.integraservicios.database.model.Building;
import com.quantumdev.integraservicios.database.model.Hardware;
import com.quantumdev.integraservicios.database.model.HardwareType;
import com.quantumdev.integraservicios.database.model.StoredHardware;
import com.quantumdev.integraservicios.database.model.Warehouse;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an entry in the stored hardware list.
 * This class is used to map the response from the database to a Java object.
 * It contains fields for building, warehouse, type, and name of the hardware.
 * The @Data annotation from Lombok generates getters, setters, equals, hashCode,
 * and toString methods automatically.
 * @author Nicolás Sabogal
 */
@Data
@Builder
public class StoredHardwareListEntry {

    // Primary Key
    private Short code_building;
    private Short code_warehouse;
    private Short code_storedhw;
    
    // Display fields
    private String name_building;
    private String name_hardware;
    private String type_hardware;
    
    /**
     * Converts a StoredHardware entity to a StoredHardwareListEntry response object.
     * @param storedHardware the StoredHardware entity to convert
     * @return              the converted StoredHardwareListEntry object
     */
    public static final StoredHardwareListEntry from(StoredHardware storedHardware) {
        Warehouse warehouse = storedHardware.getId().getWarehouse();
        Building building = warehouse.getId().getBuilding();
        Hardware hardware = storedHardware.getHardware();
        HardwareType hardwareType = hardware.getType();
        return StoredHardwareListEntry.builder()
                .code_building(building.getCode())
                .code_warehouse(warehouse.getId().getCode())
                .code_storedhw(storedHardware.getId().getCode())
                .name_building(building.getName())
                .name_hardware(hardware.getName())
                .type_hardware(hardwareType.getName())
                .build();
    }
}
