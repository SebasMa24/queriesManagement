package com.quantumdev.integraservicios.database.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
    @Column(
        name = "code_resspace",
        nullable = false
    )
    private Long code;
    
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

    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @JoinColumn(
        name = "requester_resspace",
        nullable = false
    )
    private User requester;

    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @JoinColumn(
        name = "manager_resspace",
        nullable = false
    )
    private User manager;

    @Column(
        name = "start_resspace",
        nullable = false
    )
    private Instant startDate;

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
