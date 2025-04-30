package com.quantumdev.integraservicios.queriesManagement.response;

import com.quantumdev.integraservicios.database.model.Building;
import com.quantumdev.integraservicios.database.model.SpaceType;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an entry in the space list.
 * This class is used to map the response from the database to a Java object.
 * It contains fields for building, space, type, name, and capacity of the space.
 * The @Data annotation from Lombok generates getters, setters, equals, hashCode,
 * and toString methods automatically.
 * @author Nicolás Sabogal
 */
@Data
@Builder
public class SpaceListEntry {

    // Primary Key
    private Short code_building;
    private Short code_space;

    // Display fields
    private String name_building;
    private String name_space;
    private String type_space;
    private Short capacity_space;
    
    /**
     * Converts a Space entity to a SpaceListEntry response object.
     * @param space the Space entity to convert
     * @return      the converted SpaceListEntry object
     */
    public static final SpaceListEntry from(com.quantumdev.integraservicios.database.model.Space space) {
        Building building = space.getId().getBuilding();
        SpaceType spaceType = space.getType();
        return SpaceListEntry.builder()
                .code_building(building.getCode())
                .code_space(space.getId().getCode())
                .name_building(building.getName())
                .name_space(space.getName())
                .type_space(spaceType.getName())
                .capacity_space(space.getCapacity())
                .build();
    }

}
