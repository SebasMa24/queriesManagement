package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing the composite key for the StoredHardware entity.
 * This class is used to uniquely identify a StoredHardware by its warehouse and code.
 * It implements Serializable for use as a composite key in JPA.
 * @author Nicolás Sabogal
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
