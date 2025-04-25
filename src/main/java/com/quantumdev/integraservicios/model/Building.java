package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    name = "Building",
    uniqueConstraints = @UniqueConstraint(columnNames = "code_building")
)
public class Building {

    @Id
    @NotEmpty
    @Column(
        name = "code_building",
        nullable = false
    )
    private Short code;

    @NotEmpty
    @ManyToOne(
        targetEntity = Faculty.class,
        optional = false
    )
    @Column(
        name = "faculty_building",
        length = 32,
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

    @NotEmpty
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
