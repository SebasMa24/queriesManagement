package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a StoredHardware in the database.
 * This class is mapped to the "StoredHardware" table and contains fields for stored hardware details.
 * It includes validation annotations for data integrity and constraints.
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "StoredHardware")
public class StoredHardware {

    @Id
    private StoredHardwareId id;

    @ManyToOne(
        targetEntity = Hardware.class,
        optional = false
    )
    @JoinColumn(
        name = "hardware_storedhw",
        nullable = false
    )
    private Hardware hardware;

    @ManyToOne(
        targetEntity = State.class,
        optional = false
    )
    @JoinColumn(
        name = "state_storedhw",
        nullable = false
    )
    private State state;

}
