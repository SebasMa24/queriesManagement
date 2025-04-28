package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
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
