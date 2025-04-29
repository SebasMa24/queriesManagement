package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing the composite key for the Space entity.
 * This class is used to uniquely identify a Space by its building and code.
 * It implements Serializable for use as a composite key in JPA.
 * @author Nicolás Sabogal
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
