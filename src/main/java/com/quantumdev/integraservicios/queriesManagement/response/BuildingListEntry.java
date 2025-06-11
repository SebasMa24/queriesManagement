package com.quantumdev.integraservicios.queriesManagement.response;

import com.quantumdev.integraservicios.database.model.Building;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an entry in the list of buildings.
 * This class can be extended to include specific fields
 * related to building entries as needed.
 * 
 * @author Nicolás Sabogal
 */
@Data
@Builder
public class BuildingListEntry {

    /** Building Code */
    private Short code;

    /** Buidling Name */
    private String name;

    /**
     * Converts a Building entity to a BuildingListEntry.
     * 
     * @param building the Building entity to convert.
     * @return a BuildingListEntry representing the building.
     */
    public static BuildingListEntry from(Building building) {
        return BuildingListEntry.builder()
                .code(building.getCode())
                .name(building.getName())
                .build();
    }
    
}
