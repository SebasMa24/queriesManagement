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
 * Entity class representing a Faculty in the database.
 * This class is mapped to the "Faculty" table and contains fields for faculty details.
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
    name = "Faculty",
    uniqueConstraints = @UniqueConstraint(columnNames = "name_faculty")
)
public class Faculty {

    @Id
    @NotEmpty
    @Column(
        name = "name_faculty",
        length = 32,
        nullable = false
    )
    private String name;

    @Column(
        name = "desc_faculty",
        length = 64
    )
    private String description;

}
