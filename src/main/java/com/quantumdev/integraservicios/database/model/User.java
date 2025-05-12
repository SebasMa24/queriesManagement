package com.quantumdev.integraservicios.database.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
 * Entity class representing an User in the database.
 * This class is mapped to the "AppUser" table and contains fields for user details.
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
    name = "AppUser",
    uniqueConstraints = @UniqueConstraint(columnNames = "email_user")
)
public class User {

    @Id
    @Email
    @NotEmpty
    @Column(
        name = "email_user",
        length = 64,
        nullable = false
    )
    private String email;

    @ManyToOne(
        targetEntity = Role.class,
        optional = false
    )
    @JoinColumn(
        name = "role_user",
        nullable = false
    )
    private Role role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty
    @Column(
        name = "pass_user",
        length = 32,
        nullable = false
    )
    private String password;

    @Column(name = "code_user")
    private Long code;

    @NotEmpty
    @Column(
        name = "name_user",
        length = 32,
        nullable = false
    )
    private String name;

    @Column(
        name = "phone_user",
        nullable = false
    )
    private Long phone;

    @NotEmpty
    @Column(
        name = "address_user",
        length = 64,
        nullable = false
    )
    private String address;

}
