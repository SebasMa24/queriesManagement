package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Building in the database.
 * This class is mapped to the "Building" table and contains fields for building details.
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
    name = "Building",
    uniqueConstraints = @UniqueConstraint(columnNames = "code_building")
)
public class Building {

    @Id
    @Column(
        name = "code_building",
        nullable = false
    )
    private Short code;

    @ManyToOne(
        targetEntity = Faculty.class,
        optional = false
    )
    @JoinColumn(
        name = "faculty_building",
        nullable = false
    )
    private Faculty faculty;

    @NotEmpty
    @Column(
        name = "name_building",
        length = 64,
        nullable = false
    )
    private String name;

    @Column(
        name = "phone_building",
        nullable = false
    )
    private Long phone;

    @NotEmpty
    @Column(
        name = "address_building",
        length = 64,
        nullable = false
    )
    private String address;

    @Email
    @NotEmpty
    @Column(
        name = "email_building",
        length = 64,
        nullable = false
    )
    private String email;

}
