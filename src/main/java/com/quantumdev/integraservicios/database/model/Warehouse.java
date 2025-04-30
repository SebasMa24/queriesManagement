package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Warehouse in the database.
 * This class is mapped to the "Warehouse" table and contains fields for warehouse details.
 * It includes validation annotations for data integrity and constraints.
 * @author Nicolás Sabogal
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Warehouse")
public class Warehouse {

    @Id
    private WarehouseId id;
    
}
