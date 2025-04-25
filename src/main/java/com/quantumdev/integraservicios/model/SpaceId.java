package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotEmpty;

@Embeddable
public class SpaceId {
    
    @NotEmpty
    @ManyToOne(
        targetEntity = Building.class,
        optional = false
    )
    @Column(
        name = "code_building",
        nullable = false
    )
    private Building building;

    @NotEmpty
    @Column(
        name = "code_space",
        nullable = false
    )
    private Short code;
    
}
