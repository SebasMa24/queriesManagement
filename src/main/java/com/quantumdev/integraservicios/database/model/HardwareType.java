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
 * Entity class representing a HardwareType in the database.
 * This class is mapped to the "HardwareType" table and contains fields for hardware type details.
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
    name = "HardwareType",
    uniqueConstraints = @UniqueConstraint(columnNames = "name_hardwareType")
)
public class HardwareType {

    @Id
    @NotEmpty
    @Column(
        name = "name_hardwareType",
        length = 32,
        nullable = false
    )
    private String name;

    @Column(
        name = "desc_hardwareType",
        length = 64
    )
    private String description;

}
