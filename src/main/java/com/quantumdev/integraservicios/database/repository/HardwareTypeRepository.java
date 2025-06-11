package com.quantumdev.integraservicios.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quantumdev.integraservicios.database.model.HardwareType;

/**
 * Repository interface for managing HardwareType entities.
 * This interface extends JpaRepository to provide CRUD operations for HardwareType entities.
 * It uses String as the type for the primary key of the HardwareType entity.
 * 
 * @author Nicolás Sabogal
 */
public interface HardwareTypeRepository extends JpaRepository<HardwareType, String> {
    
}
