package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    name = "State",
    uniqueConstraints = @UniqueConstraint(columnNames = "name_state")
)
public class State {

    @Id
    @NotEmpty
    @Column(
        name = "name_state",
        length = 32,
        nullable = false
    )
    private String name;

    @Column(
        name = "desc_state",
        length = 64
    )
    private String description;

}
