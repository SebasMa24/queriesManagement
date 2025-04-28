package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Class representing the composite key for the Warehouse entity.
 * This class is used to uniquely identify a Warehouse by its building and code.
 * It implements Serializable for use as a composite key in JPA.
 * @author Nicolás Sabogal
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarehouseId {

    @ManyToOne(
        targetEntity = Building.class,
        optional = false
    )
    @JoinColumn(
        name = "building_warehouse",
        nullable = false
    )
    private Building building;

    @Column(
        name = "code_warehouse",
        nullable = false
    )
    private Short code;
    
}
