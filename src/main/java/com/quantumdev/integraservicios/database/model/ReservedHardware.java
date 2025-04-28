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
    name = "ReservedHardware",
    uniqueConstraints = @UniqueConstraint(columnNames = "code_reshw")
)
public class ReservedHardware {

    @Id
    @Column(
        name = "code_reshw",
        nullable = false
    )
    private Long code;

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

    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @JoinColumn(
        name = "requester_reshw",
        nullable = false
    )
    private User requester;

    @ManyToOne(
        targetEntity = User.class,
        optional = false
    )
    @JoinColumn(
        name = "manager_reshw",
        nullable = false
    )
    private User manager;

    @Column(
        name = "start_reshw",
        nullable = false
    )
    private Instant startDate;

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
