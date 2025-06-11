package com.quantumdev.integraservicios.queriesManagement.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantumdev.integraservicios.queriesManagement.Service.DomainService;
import com.quantumdev.integraservicios.queriesManagement.response.BuildingListEntry;

import lombok.RequiredArgsConstructor;

/**
 * Controller for handling queries related to domains.
 * This controller provides endpoints to retrieve the domain data such as
 * buildings code and names, hardware type names and space type names.
 * 
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/domains")
public class DomainQueryController {

    /** Repository for accessing building data. */
    private final DomainService buildingService; 

    /**
     * Retrieves a list of all buildings in the system.
     * 
     * @return a list of BuildingListEntry objects representing the buildings.
     */
    @GetMapping("/buildings")
    public List<BuildingListEntry> getBuildingsDomain() {
        return buildingService.getAllBuildings().stream()
                .map(BuildingListEntry::from)
                .toList();
    }
    
    /**
     * Retrieves a list of all hardware types in the system.
     * 
     * @return a list of hardware type names.
     */
    @GetMapping("/hardware-types")
    public List<String> getHardwareTypesDomain() {
        return buildingService.getAllHardwareTypes().stream()
                .map(hardwareType -> hardwareType.getName())
                .toList();
    }

    /**
     * Retrieves a list of all space types in the system.
     * 
     * @return a list of space type names.
     */
    @GetMapping("/space-types")
    public List<String> getSpaceTypesDomain() {
        return buildingService.getAllSpaceTypes().stream()
                .map(spaceType -> spaceType.getName())
                .toList();
    }
    
}
