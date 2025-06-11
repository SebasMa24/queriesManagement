package com.quantumdev.integraservicios.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quantumdev.integraservicios.database.model.Building;

/**
 * Repository interface for managing Building entities.
 * This interface extends JpaRepository to provide CRUD operations for Building entities.
 * It uses Short as the type for the primary key of the Building entity.
 * 
 * @author Nicolás Sabogal
 */
public interface BuildingRepository extends JpaRepository<Building, Short> {
    
}
