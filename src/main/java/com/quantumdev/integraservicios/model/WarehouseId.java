package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotEmpty;

@Embeddable
public class WarehouseId {

    @NotEmpty
    @ManyToOne(
        targetEntity = Building.class,
        optional = false
    )
    @Column(
        name = "building_warehouse",
        nullable = false
    )
    private Building building;

    @NotEmpty
    @Column(
        name = "code_warehouse",
        nullable = false
    )
    private Short codeWarehouse;
    
}
