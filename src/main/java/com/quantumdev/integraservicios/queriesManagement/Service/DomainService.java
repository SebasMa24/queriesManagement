package com.quantumdev.integraservicios.queriesManagement.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.quantumdev.integraservicios.database.model.Building;
import com.quantumdev.integraservicios.database.model.HardwareType;
import com.quantumdev.integraservicios.database.model.SpaceType;
import com.quantumdev.integraservicios.database.repository.BuildingRepository;
import com.quantumdev.integraservicios.database.repository.HardwareTypeRepository;
import com.quantumdev.integraservicios.database.repository.SpaceTypeRepository;

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

    /** Repository for accessing HardwareType entities. */
    private final HardwareTypeRepository hardwareTypeRepository;

    /** Repository for accessing SpaceType entities. */
    private final SpaceTypeRepository spaceTypeRepository;

    /**
     * Retrieves all buildings from the database.
     * 
     * @return a list of all buildings.
     */
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    /**
     * Retrieves all hardware types from the database.
     * 
     * @return a list of all hardware types.
     */
    public List<HardwareType> getAllHardwareTypes() {
        return hardwareTypeRepository.findAll();
    }

    /**
     * Retrieves all space types from the database.
     * 
     * @return a list of all space types.
     */
    public List<SpaceType> getAllSpaceTypes() {
        return spaceTypeRepository.findAll();
    }

}
