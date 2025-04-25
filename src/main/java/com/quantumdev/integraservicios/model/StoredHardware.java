package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "StoredHardware")
public class StoredHardware {

    @Id
    private StoredHardwareId id;

    @NotEmpty
    @ManyToOne(
        targetEntity = Hardware.class,
        optional = false
    )
    @Column(
        name = "hardware_storedhw",
        nullable = false
    )
    private Hardware hardware;

    @NotEmpty
    @ManyToOne(
        targetEntity = State.class,
        optional = false
    )
    @Column(
        name = "state_storedhw",
        nullable = false
    )
    private State state;

}
