package com.quantumdev.integraservicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
