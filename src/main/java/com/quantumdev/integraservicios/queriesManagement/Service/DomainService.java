package com.quantumdev.integraservicios.queriesManagement.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quantumdev.integraservicios.database.model.Building;
import com.quantumdev.integraservicios.database.repository.BuildingRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service class for managing domain-related queries.
 * This service provides methods to retrieve domain data such as
 * buildings code and names, hardware type names, and space type names.
 * 
 * @author Nicolás Sabogal
 */
@Service
@RequiredArgsConstructor
public class DomainService {

    /** Repository for accessing Building entities. */
    private final BuildingRepository buildingRepository;

    /**
     * Retrieves all buildings from the database.
     * 
     * @return a list of all buildings.
     */
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

}
