package com.quantumdev.integraservicios.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Warehouse")
public class Warehouse {

    @Id
    private WarehouseId id;
    
}
