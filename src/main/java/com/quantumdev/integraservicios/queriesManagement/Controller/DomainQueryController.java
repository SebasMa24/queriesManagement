package com.quantumdev.integraservicios.queriesManagement.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getBuildingsDomain() {
        try {
            List<BuildingListEntry> buildings = buildingService.getAllBuildings().stream()
                    .map(BuildingListEntry::from)
                    .toList();

            return ResponseEntity.ok(buildings);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred while retrieving buildings: " + e.getMessage());
        }
    }
    
    /**
     * Retrieves a list of all hardware types in the system.
     * 
     * @return a list of hardware type names.
     */
    @GetMapping("/hardware-types")
    public ResponseEntity<?> getHardwareTypesDomain() {
        try {
            List<String> hardwareTypes = buildingService.getAllHardwareTypes().stream()
                    .map(hardwareType -> hardwareType.getName())
                    .toList();

            return ResponseEntity.ok(hardwareTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred while retrieving hardware types: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all space types in the system.
     * 
     * @return a list of space type names.
     */
    @GetMapping("/space-types")
    public ResponseEntity<?> getSpaceTypesDomain() {
        try {
            List<String> spaceTypes = buildingService.getAllSpaceTypes().stream()
                    .map(spaceType -> spaceType.getName())
                    .toList();

            return ResponseEntity.ok(spaceTypes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred while retrieving space types: " + e.getMessage());
        }
    }
    
}
