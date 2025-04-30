package com.quantumdev.integraservicios.queriesManagement.response;

import java.time.LocalDate;
import java.time.ZoneId;

import com.quantumdev.integraservicios.database.model.Building;
import com.quantumdev.integraservicios.database.model.ReservedSpace;
import com.quantumdev.integraservicios.database.model.Space;
import com.quantumdev.integraservicios.database.model.SpaceType;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an entry in the reserved space list.
 * This class is used to map the response from the database to a Java object.
 * It contains fields for building, space, type, name, capacity, and reservation date of the space.
 * The @Data annotation from Lombok generates getters, setters, equals, hashCode,
 * and toString methods automatically.
 * @author Nicolás Sabogal
 */
@Data
@Builder
public class ReservedSpaceListEntry {

    // Primary Key
    private Long code_resspace;

    // Display fields
    private String name_building;
    private Short code_space;
    private String name_space;
    private String type_space;
    private Short capacity_space;
    private LocalDate day_resspace;

    /**
     * Converts a ReservedSpace entity to a ReservedSpaceListEntry response object.
     * @param reservedSpace the ReservedSpace entity to convert
     * @return             the converted ReservedSpaceListEntry object
     */
    public static final ReservedSpaceListEntry from(ReservedSpace reservedSpace) {
        Space space = reservedSpace.getSpace();
        Building building = space.getId().getBuilding();
        SpaceType spaceType = space.getType();
        return ReservedSpaceListEntry.builder()
                .code_resspace(reservedSpace.getCode())
                .name_building(building.getName())
                .code_space(space.getId().getCode())
                .name_space(space.getName())
                .type_space(spaceType.getName())
                .capacity_space(space.getCapacity())
                .day_resspace(
                    reservedSpace
                        .getStartDate()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                )
                .build();
    }
    
}
