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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "SpaceType",
    uniqueConstraints = @UniqueConstraint(columnNames = "name_spaceType")
)
public class SpaceType {

    @Id
    @NotEmpty
    @Column(
        name = "name_spaceType",
        length = 32,
        nullable = false
    )
    private String name;

    @Column(
        name = "desc_spaceType",
        length = 64
    )
    private String description;

}
