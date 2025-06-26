package com.quantumdev.integraservicios.queriesManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quantumdev.integraservicios.database.model.*;
import com.quantumdev.integraservicios.queriesManagement.Controller.ResourcesQueryController;
import com.quantumdev.integraservicios.queriesManagement.Service.SpaceService;
import com.quantumdev.integraservicios.queriesManagement.Service.StoredHardwareService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceQueryControllerTests {
    
    @Mock
    private StoredHardwareService storedHardwareService;
    
    @Mock
    private SpaceService spaceService;
    
    @InjectMocks
    private ResourcesQueryController resourcesQueryController;
    
    private Faculty mockFaculty;
    private Building mockBuilding;
    private Warehouse mockWarehouse;
    private State mockState;
    private SpaceType mockSpaceType;
    private Space mockSpace;
    private HardwareType mockHardwareType;
    private Hardware mockHardware;
    private StoredHardware mockStoredHardware;
    
    @BeforeEach
    void setUp() {
        // Setup mock entities
        mockFaculty = Faculty.builder()
            .name("Engineering Faculty")
            .description("Faculty of Engineering Sciences")
            .build();
            
        mockBuilding = Building.builder()
            .code((short) 1)
            .name("Building A")
            .faculty(mockFaculty)
            .phone(1234567890L)
            .address("123 Main St")
            .email("building@university.edu")
            .build();
            
        WarehouseId warehouseId = WarehouseId.builder()
            .building(mockBuilding)
            .code((short) 101)
            .build();
            
        mockWarehouse = Warehouse.builder()
            .id(warehouseId)
            .build();
            
        mockState = State.builder()
            .name("AVAILABLE")
            .description("Available for use")
            .build();

        mockSpaceType = SpaceType.builder()
            .name("Laboratory")
            .description("Computer lab for students")
            .build();

        SpaceId spaceId = SpaceId.builder()
            .building(mockBuilding)
            .code((short) 101)
            .build();
        
        mockSpace = Space.builder()
            .id(spaceId)
            .name("Computer Lab 101")
            .type(mockSpaceType)
            .schedule("M0812,T0915")
            .description("Main computer lab for engineering students")
            .build();
            
        mockHardwareType = HardwareType.builder()
            .name("Laptop")
            .description("Portable computer")
            .build();
            
        mockHardware = Hardware.builder()
            .code(1L)
            .name("Dell Laptop")
            .type(mockHardwareType)
            .schedule("M0812,T0915")
            .description("Dell laptop for development")
            .build();

        StoredHardwareId storedHardwareId = StoredHardwareId.builder()
            .warehouse(mockWarehouse)
            .code((short) 1)
            .build();
            
        mockStoredHardware = StoredHardware.builder()
            .id(storedHardwareId)
            .hardware(mockHardware)
            .state(mockState)
            .build();
    }
    
    // Tests for getHardwareResource method
    @Test
    void testGetHardwareResource_ValidParameters_ReturnsStoredHardware() {
        // Arrange
        Short building = (short) 1;
        Short warehouse = (short) 101;
        Short id = (short) 1;
        when(storedHardwareService.getDetails(building, warehouse, id)).thenReturn(mockStoredHardware);
        
        // Act
        StoredHardware result = (StoredHardware) resourcesQueryController.getHardwareResource(building, warehouse, id).getBody();
        
        // Assert
        assertNotNull(result);
        assertEquals(mockStoredHardware, result);
        verify(storedHardwareService).getDetails(building, warehouse, id);
    }
    
    @Test
    void testGetHardwareResource_InvalidParameters_ReturnsNull() {
        // Arrange
        Short building = (short) 1;
        Short warehouse = (short) 101;
        Short id = (short) 999;
        when(storedHardwareService.getDetails(building, warehouse, id)).thenReturn(null);
        
        // Act
        ResponseEntity<?> response = resourcesQueryController.getHardwareResource(building, warehouse, id);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(storedHardwareService).getDetails(building, warehouse, id);
    }
    
    @Test
    void testGetHardwareResource_ServiceThrowsException_ReturnsInternalServerError() {
        // Arrange
        Short building = (short) 1;
        Short warehouse = (short) 101;
        Short id = (short) 1;
        when(storedHardwareService.getDetails(building, warehouse, id)).thenThrow(new RuntimeException("Database connection error"));
        
        // Act
        ResponseEntity<?> response = resourcesQueryController.getHardwareResource(building, warehouse, id);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Unexpected error occurred while retrieving stored hardware"));
        verify(storedHardwareService).getDetails(building, warehouse, id);
    }
    
    // Tests for getSpaceResource method
    @Test
    void testGetSpaceResource_ValidParameters_ReturnsSpace() {
        // Arrange
        Short building = (short) 1;
        Short id = (short) 101;
        when(spaceService.getDetails(building, id)).thenReturn(mockSpace);
        
        // Act
        Space result = (Space) resourcesQueryController.getSpaceResource(building, id).getBody();
        
        // Assert
        assertNotNull(result);
        assertEquals(mockSpace, result);
        verify(spaceService).getDetails(building, id);
    }
    
    @Test
    void testGetSpaceResource_InvalidParameters_ReturnsNull() {
        // Arrange
        Short building = (short) 1;
        Short id = (short) 999;
        when(spaceService.getDetails(building, id)).thenReturn(null);
        
        // Act
        ResponseEntity<?> response = resourcesQueryController.getSpaceResource(building, id);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(spaceService).getDetails(building, id);
    }
    
    @Test
    void testGetSpaceResource_ServiceThrowsException_ReturnsInternalServerError() {
        // Arrange
        Short building = (short) 1;
        Short id = (short) 101;
        when(spaceService.getDetails(building, id)).thenThrow(new RuntimeException("Database connection error"));
        
        // Act
        ResponseEntity<?> response = resourcesQueryController.getSpaceResource(building, id);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Unexpected error occurred while retrieving space"));
        verify(spaceService).getDetails(building, id);
    }
    
}
