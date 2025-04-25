package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotEmpty;

@Embeddable
public class StoredHardwareId {

    @NotEmpty
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

    @NotEmpty
    @Column(
        name = "code_storedhw",
        nullable = false
    )
    private Short codeStoredHardware;
    
}
