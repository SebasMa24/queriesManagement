package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Embeddable
public class StoredHardwareId {

    @ManyToOne(
        targetEntity = Warehouse.class,
        optional = false
    )
    @JoinColumns({
        @JoinColumn(
            name = "building_storedhw",
            referencedColumnName = "building_warehouse",
            nullable = false
        ),
        @JoinColumn(
            name = "warehouse_storedhw",
            referencedColumnName = "code_warehouse",
            nullable = false
        )
    })
    private Warehouse warehouse;

    @Column(
        name = "code_storedhw",
        nullable = false
    )
    private Short code;
    
}
