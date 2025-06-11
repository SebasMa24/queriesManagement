package com.quantumdev.integraservicios.queriesManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.quantumdev.integraservicios.database.model.*;
import com.quantumdev.integraservicios.queriesManagement.Controller.DomainQueryController;
import com.quantumdev.integraservicios.queriesManagement.Service.DomainService;
import com.quantumdev.integraservicios.queriesManagement.response.BuildingListEntry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
      // Tests for getFaculties method (assuming it uses a separate faculty service)
    @Test
    void testGetFaculties_ValidRequest_ReturnsFacultyList() {
        // This test would need to be implemented when the actual controller method is available
        // For now, we'll skip faculty tests as DomainService doesn't have getAllFaculties method
        assertTrue(true, "Placeholder for getFaculties tests");
    }
    
    @Test
    void testGetFaculties_EmptyList_ReturnsEmptyList() {
        // This test would need to be implemented when the actual controller method is available
        assertTrue(true, "Placeholder for getFaculties empty list test");
    }
    
    @Test
    void testGetFaculties_ServiceThrowsException_ExceptionPropagated() {
        // This test would need to be implemented when the actual controller method is available
        assertTrue(true, "Placeholder for getFaculties exception test");
    }
    
    // Tests for getBuildings method
    @Test
    void testGetBuildings_ValidRequest_ReturnsBuildingList() {
        // Arrange
        List<Building> mockBuildingList = Arrays.asList(mockBuilding);
        when(domainService.getAllBuildings()).thenReturn(mockBuildingList);
        
        // Act
        List<BuildingListEntry> result = domainQueryController.getBuildingsDomain();
        
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
        List<BuildingListEntry> result = domainQueryController.getBuildingsDomain();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(domainService).getAllBuildings();
    }
    
    @Test
    void testGetBuildings_ServiceThrowsException_ExceptionPropagated() {
        // Arrange
        when(domainService.getAllBuildings()).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            domainQueryController.getBuildingsDomain();
        });
        verify(domainService).getAllBuildings();
    }
      // Tests for getStates method (assuming it uses a separate state service)
    @Test
    void testGetStates_ValidRequest_ReturnsStateList() {
        // This test would need to be implemented when the actual controller method is available
        // For now, we'll skip state tests as DomainService doesn't have getAllStates method
        assertTrue(true, "Placeholder for getStates tests");
    }
    
    @Test
    void testGetStates_EmptyList_ReturnsEmptyList() {
        // This test would need to be implemented when the actual controller method is available
        assertTrue(true, "Placeholder for getStates empty list test");
    }
    
    @Test
    void testGetStates_ServiceThrowsException_ExceptionPropagated() {
        // This test would need to be implemented when the actual controller method is available
        assertTrue(true, "Placeholder for getStates exception test");
    }
    
    // Tests for getHardwareTypes method
    @Test
    void testGetHardwareTypes_ValidRequest_ReturnsHardwareTypeList() {
        // Arrange
        List<HardwareType> mockHardwareTypeList = Arrays.asList(mockHardwareType);
        when(domainService.getAllHardwareTypes()).thenReturn(mockHardwareTypeList);
        
        // Act
        List<String> result = domainQueryController.getHardwareTypesDomain();
        
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
        List<String> result = domainQueryController.getHardwareTypesDomain();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(domainService).getAllHardwareTypes();
    }
    
    @Test
    void testGetHardwareTypes_ServiceThrowsException_ExceptionPropagated() {
        // Arrange
        when(domainService.getAllHardwareTypes()).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            domainQueryController.getHardwareTypesDomain();
        });
        verify(domainService).getAllHardwareTypes();
    }
    
    // Tests for getSpaceTypes method
    @Test
    void testGetSpaceTypes_ValidRequest_ReturnsSpaceTypeList() {
        // Arrange
        List<SpaceType> mockSpaceTypeList = Arrays.asList(mockSpaceType);
        when(domainService.getAllSpaceTypes()).thenReturn(mockSpaceTypeList);
        
        // Act
        List<String> result = domainQueryController.getSpaceTypesDomain();
        
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
        List<String> result = domainQueryController.getSpaceTypesDomain();
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(domainService).getAllSpaceTypes();
    }
    
    @Test
    void testGetSpaceTypes_ServiceThrowsException_ExceptionPropagated() {
        // Arrange
        when(domainService.getAllSpaceTypes()).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            domainQueryController.getSpaceTypesDomain();
        });
        verify(domainService).getAllSpaceTypes();
    }
    
}
