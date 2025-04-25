package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
    name = "Hardware",
    uniqueConstraints = @UniqueConstraint(columnNames = "code_hardware")
)
public class Hardware {

    @Id
    @NotEmpty
    @Column(
        name = "code_hardware",
        nullable = false
    )
    private Long code;

    @NotEmpty
    @ManyToOne(
        targetEntity = HardwareType.class,
        optional = false
    )
    @Column(
        name = "type_hardware",
        nullable = false
    )
    private HardwareType type;
    
    @NotEmpty
    @Column(
        name = "name_hardware",
        length = 32,
        nullable = false
    )
    private String name;

    @NotEmpty
    @Column(
        name = "schedule_hardware",
        length = 49,
        nullable = false
    )
    private String schedule;

    @Column(
        name = "desc_hardware",
        length = 64,
        nullable = true
    )
    private String description;
    
}
