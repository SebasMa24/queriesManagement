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
import com.quantumdev.integraservicios.queriesManagement.Controller.DomainQueryController;
import com.quantumdev.integraservicios.queriesManagement.Service.DomainService;
import com.quantumdev.integraservicios.queriesManagement.response.BuildingListEntry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked"})
@ExtendWith(MockitoExtension.class)
public class DomainQueryControllerTests {
    
    @Mock
    private DomainService domainService;
    
    @InjectMocks
    private DomainQueryController domainQueryController;
    
    private Faculty mockFaculty;
    private Building mockBuilding;
    private HardwareType mockHardwareType;
    private SpaceType mockSpaceType;
    
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
            
        mockHardwareType = HardwareType.builder()
            .name("Laptop")
            .description("Portable computer")
            .build();
            
        mockSpaceType = SpaceType.builder()
            .name("Laboratory")
            .description("Computer lab for students")
            .build();
    }
    
    // Tests for getBuildings method
    @Test
    void testGetBuildings_ValidRequest_ReturnsBuildingList() {
        // Arrange
        List<Building> mockBuildingList = Arrays.asList(mockBuilding);
        when(domainService.getAllBuildings()).thenReturn(mockBuildingList);
        
        // Act
        List<BuildingListEntry> result = (List<BuildingListEntry>) domainQueryController.getBuildingsDomain().getBody();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(BuildingListEntry.from(mockBuilding), result.get(0));
        verify(domainService).getAllBuildings();
    }
    
    @Test
    void testGetBuildings_EmptyList_ReturnsEmptyList() {
        // Arrange
        when(domainService.getAllBuildings()).thenReturn(Collections.emptyList());
        
        // Act
        List<BuildingListEntry> result = (List<BuildingListEntry>) domainQueryController.getBuildingsDomain().getBody();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(domainService).getAllBuildings();
    }
    
    @Test
    void testGetBuildings_ServiceThrowsException_ReturnsServerError() {
        // Arrange
        when(domainService.getAllBuildings()).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = domainQueryController.getBuildingsDomain();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Unexpected error occurred while retrieving buildings"));
    }

    // Tests for getHardwareTypes method
    @Test
    void testGetHardwareTypes_ValidRequest_ReturnsHardwareTypeList() {
        // Arrange
        List<HardwareType> mockHardwareTypeList = Arrays.asList(mockHardwareType);
        when(domainService.getAllHardwareTypes()).thenReturn(mockHardwareTypeList);
        
        // Act
        List<String> result = (List<String>) domainQueryController.getHardwareTypesDomain().getBody();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockHardwareType.getName(), result.get(0));
        verify(domainService).getAllHardwareTypes();
    }
    
    @Test
    void testGetHardwareTypes_EmptyList_ReturnsEmptyList() {
        // Arrange
        when(domainService.getAllHardwareTypes()).thenReturn(Collections.emptyList());
        
        // Act
        List<String> result = (List<String>) domainQueryController.getHardwareTypesDomain().getBody();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(domainService).getAllHardwareTypes();
    }
    
    @Test
    void testGetHardwareTypes_ServiceThrowsException_ReturnsServerError() {
        // Arrange
        when(domainService.getAllHardwareTypes()).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = domainQueryController.getHardwareTypesDomain();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Unexpected error occurred while retrieving hardware types"));
        verify(domainService).getAllHardwareTypes();
    }
    
    // Tests for getSpaceTypes method
    @Test
    void testGetSpaceTypes_ValidRequest_ReturnsSpaceTypeList() {
        // Arrange
        List<SpaceType> mockSpaceTypeList = Arrays.asList(mockSpaceType);
        when(domainService.getAllSpaceTypes()).thenReturn(mockSpaceTypeList);
        
        // Act
        List<String> result = (List<String>) domainQueryController.getSpaceTypesDomain().getBody();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(mockSpaceType.getName(), result.get(0));
        verify(domainService).getAllSpaceTypes();
    }
    
    @Test
    void testGetSpaceTypes_EmptyList_ReturnsEmptyList() {
        // Arrange
        when(domainService.getAllSpaceTypes()).thenReturn(Collections.emptyList());
        
        // Act
        List<String> result = (List<String>) domainQueryController.getSpaceTypesDomain().getBody();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(domainService).getAllSpaceTypes();
    }
    
    @Test
    void testGetSpaceTypes_ServiceThrowsException_ReturnsServerError() {
        // Arrange
        when(domainService.getAllSpaceTypes()).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = domainQueryController.getSpaceTypesDomain();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Unexpected error occurred while retrieving space types"));
        verify(domainService).getAllSpaceTypes();
    }
    
}
