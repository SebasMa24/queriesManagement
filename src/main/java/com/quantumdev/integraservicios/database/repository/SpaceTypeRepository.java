package com.quantumdev.integraservicios.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quantumdev.integraservicios.database.model.SpaceType;

/**
 * Repository interface for managing SpaceType entities.
 * This interface extends JpaRepository to provide CRUD operations for SpaceType entities.
 * It uses String as the type for the primary key of the SpaceType entity.
 * 
 * @author Nicolás Sabogal
 */
public interface SpaceTypeRepository extends JpaRepository<SpaceType, String> {
    
}
