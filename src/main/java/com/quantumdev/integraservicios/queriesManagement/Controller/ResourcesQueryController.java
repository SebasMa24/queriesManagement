package com.quantumdev.integraservicios.queriesManagement.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantumdev.integraservicios.database.model.StoredHardware;
import com.quantumdev.integraservicios.queriesManagement.Service.SpaceService;
import com.quantumdev.integraservicios.queriesManagement.Service.StoredHardwareService;

import lombok.RequiredArgsConstructor;

/**
 * Controller for handling queries related to resources such as stored hardware and spaces.
 * This controller provides endpoints to retrieve details of specific hardware and space resources.
 * 
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resources")
public class ResourcesQueryController {

    /** Service for managing stored hardware resources. */
    private final StoredHardwareService storedHardwareService;
    /** Service for managing space resources. */
    private final SpaceService spaceService;

    /**
     * Retrieves details of a specific stored hardware resource by its composite key.
     * 
     * @param building  The building number.
     * @param warehouse The warehouse number.
     * @param id        The ID of the stored hardware item.
     * @return ResponseEntity containing the stored hardware details.
     */
    @GetMapping("/hardware/{building}-{warehouse}-{id}")
    public ResponseEntity<?> getHardwareResource(
        @PathVariable Short building,
        @PathVariable Short warehouse,
        @PathVariable Short id
    ) {
        try {
            StoredHardware storedHardware = storedHardwareService.getDetails(building, warehouse, id);
            return ResponseEntity.ok(storedHardware);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Unexpected error occurred while retrieving stored hardware: " + e.getMessage());
        }
    }

    /**
     * Retrieves details of a specific space resource by its composite key.
     * 
     * @param building The building number.
     * @param id       The ID of the space.
     * @return ResponseEntity containing the space details.
     */
    @GetMapping("/space/{building}-{id}")
    public ResponseEntity<?> getSpaceResource(
        @PathVariable Short building,
        @PathVariable Short id
    ) {
        try {
            var space = spaceService.getDetails(building, id);
            return ResponseEntity.ok(space);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("Unexpected error occurred while retrieving space: " + e.getMessage());
        }
    }
    
}
