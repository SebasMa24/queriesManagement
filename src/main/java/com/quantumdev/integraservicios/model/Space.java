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
@Table(name = "Space")
public class Space {

    @Id
    private SpaceId id;

    @NotEmpty
    @ManyToOne(
        targetEntity = SpaceType.class,
        optional = false
    )
    @Column(
        name = "type_space",
        nullable = false
    )
    private SpaceType type;

    @NotEmpty
    @ManyToOne(
        targetEntity = State.class,
        optional = false
    )
    @Column(
        name = "state_space",
        nullable = false
    )
    private State state;

    @NotEmpty
    @Column(
        name = "name_space",
        length = 64,
        nullable = false
    )
    private String name;

    @NotEmpty
    @Column(
        name = "capacity_space",
        nullable = false
    )
    private Short capacity;

    @NotEmpty
    @Column(
        name = "schedule_space",
        length = 49,
        nullable = false
    )
    private String schedule;

    @Column(
        name = "desc_space",
        length = 64,
        nullable = true
    )
    private String description;

}
