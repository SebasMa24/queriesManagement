package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a State in the database.
 * This class is mapped to the "State" table and contains fields for state details.
 * It includes validation annotations for data integrity and constraints.
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
