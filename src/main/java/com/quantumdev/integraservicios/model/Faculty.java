package com.quantumdev.integraservicios.model;

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
