package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class SpaceId {
    
    @ManyToOne(
        targetEntity = Building.class,
        optional = false
    )
    @JoinColumn(
        name = "building_space",
        nullable = false
    )
    private Building building;

    @Column(
        name = "code_space",
        nullable = false
    )
    private Short code;
    
}
