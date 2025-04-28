package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
    name = "Role",
    uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Role {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(
        name = "name_role",
        length = 32,
        nullable = false
    )
    private ERole name;

    @Column(
        name = "desc_role",
        length = 64
    )
    private String description;

}
