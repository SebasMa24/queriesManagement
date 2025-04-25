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
    name = "ReservedHardware",
    uniqueConstraints = @UniqueConstraint(columnNames = "code_reshw")
)
public class ReservedHardware {

    @Id
    @NotEmpty
    @Column(
        name = "code_reshw",
        nullable = false
    )
    private Long code;

    @NotEmpty
    @ManyToOne(
        targetEntity = StoredHardware.class,
        optional = false
    )
    @JoinColumns({
        @JoinColumn(
            name = "building_reshw",
            referencedColumnName = "building_storedhw",
            nullable = false
        ),
        @JoinColumn(
            name = "warehouse_reshw",
            referencedColumnName = "warehouse_storedhw",
            nullable = false
        ),
        @JoinColumn(
            name = "storedhw_reshw",
            referencedColumnName = "code_storedhw",
            nullable = false
        )
    })
    private StoredHardware storedHardware;

    @NotEmpty
    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @Column(
        name = "requester_reshw",
        nullable = false
    )
    private User requester;

    @NotEmpty
    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @Column(
        name = "manager_reshw",
        nullable = false
    )
    private User manager;

    @NotEmpty
    @Column(
        name = "start_reshw",
        nullable = false
    )
    private Instant startDate;

    @NotEmpty
    @Column(
        name = "end_reshw",
        nullable = false
    )
    private Instant endDate;

    @Column(name = "handover_reshw")
    private Instant handoverDate;

    @Column(name = "return_reshw")
    private Instant returnDate;

    @Column(name = "condrate_reshw")
    private Byte conditionRate;

    @Column(name = "serrate_reshw")
    private Byte serviceRate;

}
