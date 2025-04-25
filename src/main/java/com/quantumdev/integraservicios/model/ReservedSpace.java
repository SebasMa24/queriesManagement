package com.quantumdev.integraservicios.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
    name = "ReservedSpace",
    uniqueConstraints = @UniqueConstraint(columnNames = "code_resspace")
)
public class ReservedSpace {
    
    @Id
    @NotEmpty
    @Column(
        name = "code_resspace",
        nullable = false
    )
    private Long code;
    
    @NotEmpty
    @ManyToOne(
        targetEntity = Space.class,
        optional = false
    )
    @JoinColumns({
        @JoinColumn(
            name = "building_resspace",
            referencedColumnName = "building_space",
            nullable = false
        ),
        @JoinColumn(
            name = "space_resspace",
            referencedColumnName = "code_space",
            nullable = false
        )
    })
    private Space space;

    @NotEmpty
    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @Column(
        name = "requester_resspace",
        nullable = false
    )
    private User requester;

    @NotEmpty
    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @Column(
        name = "manager_resspace",
        nullable = false
    )
    private User manager;

    @NotEmpty
    @Column(
        name = "start_resspace",
        nullable = false
    )
    private Instant startDate;

    @NotEmpty
    @Column(
        name = "end_resspace",
        nullable = false
    )
    private Instant endDate;

    @Column(name = "handover_resspace")
    private Instant handoverDate;

    @Column(name = "return_resspace")
    private Instant returnDate;

    @Column(name = "condrate_resspace")
    private Byte conditionRate;

    @Column(name = "serrate_resspace")
    private Byte serviceRate;

}
