package com.quantumdev.integraservicios.queriesManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.quantumdev.integraservicios.database.model.*;
import com.quantumdev.integraservicios.queriesManagement.Controller.OperationQueryController;
import com.quantumdev.integraservicios.queriesManagement.Service.StoredHardwareService;
import com.quantumdev.integraservicios.queriesManagement.Service.ReservedHardwareService;
import com.quantumdev.integraservicios.queriesManagement.Service.ReservedSpaceService;
import com.quantumdev.integraservicios.queriesManagement.Service.SpaceService;
import com.quantumdev.integraservicios.queriesManagement.response.ReservedHardwareListEntry;
import com.quantumdev.integraservicios.queriesManagement.response.ReservedSpaceListEntry;
import com.quantumdev.integraservicios.queriesManagement.response.SpaceListEntry;
import com.quantumdev.integraservicios.queriesManagement.response.StoredHardwareListEntry;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unchecked" })
@ExtendWith(MockitoExtension.class)
public class OperationQueryControllerTests {
    
    @Mock
    private StoredHardwareService storedHardwareService;
    
    @Mock
    private SpaceService spaceService;
    
    @Mock
    private ReservedHardwareService reservedHardwareService;
    
    @Mock
    private ReservedSpaceService reservedSpaceService;
    
    @InjectMocks
    private OperationQueryController operationQueryController;
    
    private User mockRequester;
    private User mockManager;
    private Faculty mockFaculty;
    private Building mockBuilding;
    private Warehouse mockWarehouse;
    private State mockState;
    private SpaceType mockSpaceType;
    private Space mockSpace;
    private ReservedSpace mockReservedSpace;
    private HardwareType mockHardwareType;
    private Hardware mockHardware;
    private StoredHardware mockStoredHardware;
    private ReservedHardware mockReservedHardware;

    private UserDetails mockUserDetails;
    private Authentication mockAuthentication;
    private SecurityContext mockSecurityContext;
    private GrantedAuthority mockAuthority;
    
    @BeforeEach
    void setUp() {
        // Setup mock entities
        mockRequester = User.builder()
            .email("student@university.edu")
            .name("John Doe")
            .build();
            
        mockManager = User.builder()
            .email("manager@university.edu")
            .name("Jane Smith")
            .build();

        mockFaculty = Faculty.builder()
            .name("Engineering Faculty")
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
            .build();        // Setup mock users for reservations

        mockReservedSpace = ReservedSpace.builder()
            .code(1L)
            .space(mockSpace)
            .requester(mockRequester)
            .manager(mockManager)
            .startDate(Instant.now())
            .endDate(Instant.now().plusSeconds(7200))
            .handoverDate(Instant.now().plusSeconds(300))
            .returnDate(Instant.now().plusSeconds(6900))
            .conditionRate((byte) 5)
            .serviceRate((byte) 4)
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

        mockReservedHardware = ReservedHardware.builder()
            .code(1L)
            .storedHardware(mockStoredHardware)
            .requester(mockRequester)
            .manager(mockManager)
            .startDate(Instant.now())
            .endDate(Instant.now().plusSeconds(3600))
            .handoverDate(Instant.now().plusSeconds(300))
            .returnDate(Instant.now().plusSeconds(3300))
            .conditionRate((byte) 5)
            .serviceRate((byte) 5)
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

        mockReservedHardware = ReservedHardware.builder()
            .code(1L)
            .storedHardware(mockStoredHardware)
            .requester(mockRequester)
            .manager(mockManager)
            .startDate(Instant.now())
            .endDate(Instant.now().plusSeconds(3600))
            .handoverDate(Instant.now().plusSeconds(300))
            .returnDate(Instant.now().plusSeconds(3300))
            .conditionRate((byte) 5)
            .serviceRate((byte) 5)
            .build();        
            
        // Setup mock security context components (will be configured in individual tests)
        mockAuthority = new SimpleGrantedAuthority("ROLE_USER");
        mockUserDetails = mock(UserDetails.class);
        mockAuthentication = mock(Authentication.class);
        mockSecurityContext = mock(SecurityContext.class);

    }
    
    // Tests for getReservedHardware method
    @Test
    void testGetReservedHardware_ValidId_ReturnsReservedHardware() {
        // Arrange
        Long hardwareId = 1L;
        when(reservedHardwareService.getById(hardwareId)).thenReturn(mockReservedHardware);
        
        // Act
        ReservedHardware result = operationQueryController.getReservedHardware(hardwareId);
        
        // Assert
        assertNotNull(result);
        assertEquals(mockReservedHardware, result);
        verify(reservedHardwareService).getById(hardwareId);
    }
    
    @Test
    void testGetReservedHardware_InvalidId_ReturnsNull() {
        // Arrange
        Long hardwareId = 999L;
        when(reservedHardwareService.getById(hardwareId)).thenReturn(null);
        
        // Act
        ReservedHardware result = operationQueryController.getReservedHardware(hardwareId);
        
        // Assert
        assertNull(result);
        verify(reservedHardwareService).getById(hardwareId);
    }
    
    @Test
    void testGetReservedHardware_ServiceThrowsException_ExceptionPropagated() {
        // Arrange
        Long hardwareId = 1L;
        when(reservedHardwareService.getById(hardwareId)).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            operationQueryController.getReservedHardware(hardwareId);
        });
        verify(reservedHardwareService).getById(hardwareId);
    }
    
    // Tests for getReservedSpace method
    @Test
    void testGetReservedSpace_ValidId_ReturnsReservedSpace() {
        // Arrange
        Long spaceId = 1L;
        when(reservedSpaceService.getById(spaceId)).thenReturn(mockReservedSpace);
        
        // Act
        ReservedSpace result = operationQueryController.getReservedSpace(spaceId);
        
        // Assert
        assertNotNull(result);
        assertEquals(mockReservedSpace, result);
        verify(reservedSpaceService).getById(spaceId);
    }
    
    @Test
    void testGetReservedSpace_InvalidId_ReturnsNull() {
        // Arrange
        Long spaceId = 999L;
        when(reservedSpaceService.getById(spaceId)).thenReturn(null);
        
        // Act
        ReservedSpace result = operationQueryController.getReservedSpace(spaceId);
        
        // Assert
        assertNull(result);
        verify(reservedSpaceService).getById(spaceId);
    }
    
    @Test
    void testGetReservedSpace_ServiceThrowsException_ExceptionPropagated() {
        // Arrange
        Long spaceId = 1L;
        when(reservedSpaceService.getById(spaceId)).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            operationQueryController.getReservedSpace(spaceId);
        });
        verify(reservedSpaceService).getById(spaceId);
    }
    
    // Tests for getAvailableItems method
    @Test
    void testGetAvailableItems_WithAllParameters_ReturnsStoredHardwareList() {
        // Arrange
        List<StoredHardware> mockStoredHardwareList = Arrays.asList(mockStoredHardware);
        when(storedHardwareService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockStoredHardwareList);
        
        // Act
        List<StoredHardwareListEntry> result = operationQueryController.getAvailableItems(
            "Dell", "Laptop", (short) 1, Instant.now(), Instant.now().plusSeconds(3600), 
            false, 10, 0, "name", false
        );
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(storedHardwareService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
    
    @Test
    void testGetAvailableItems_WithMinimalParameters_ReturnsStoredHardwareList() {
        // Arrange
        List<StoredHardware> mockStoredHardwareList = Arrays.asList(mockStoredHardware);
        when(storedHardwareService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockStoredHardwareList);
        
        // Act
        List<StoredHardwareListEntry> result = operationQueryController.getAvailableItems(
            null, null, null, null, null, null, 10, 0, null, null
        );
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(storedHardwareService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
    
    @Test
    void testGetAvailableItems_EmptyList_ReturnsEmptyList() {
        // Arrange
        when(storedHardwareService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(Collections.emptyList());
        
        // Act
        List<StoredHardwareListEntry> result = operationQueryController.getAvailableItems(
            null, null, null, null, null, null, 10, 0, null, null
        );
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(storedHardwareService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
    
    // Tests for getAvailableSpaces method
    @Test
    void testGetAvailableSpaces_WithAllParameters_ReturnsSpaceList() {
        // Arrange
        List<Space> mockSpaceList = Arrays.asList(mockSpace);
        when(spaceService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockSpaceList);
        
        // Act
        List<SpaceListEntry> result = operationQueryController.getAvailableSpaces(
            "Computer", "Laboratory", (short) 30, (short) 1, Instant.now(), 
            Instant.now().plusSeconds(3600), false, 10, 0, "name", true
        );
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(spaceService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
    
    @Test
    void testGetAvailableSpaces_WithMinimalParameters_ReturnsSpaceList() {
        // Arrange
        List<Space> mockSpaceList = Arrays.asList(mockSpace);
        when(spaceService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockSpaceList);
        
        // Act
        List<SpaceListEntry> result = operationQueryController.getAvailableSpaces(
            null, null, null, null, null, null, null, 10, 0, null, null
        );
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(spaceService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
    
    @Test
    void testGetAvailableSpaces_EmptyList_ReturnsEmptyList() {
        // Arrange
        when(spaceService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(Collections.emptyList());
        
        // Act
        List<SpaceListEntry> result = operationQueryController.getAvailableSpaces(
            null, null, null, null, null, null, null, 10, 0, null, null
        );
        
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(spaceService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
    }
    
    // Tests for getItemHistory method
    @Test
    void testGetItemHistory_AsAdmin_ReturnsReservedHardwareList() {
        // Arrange
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        when(mockUserDetails.getAuthorities()).thenReturn((Collection) Arrays.asList(adminAuthority));
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        
        List<ReservedHardware> mockReservedList = Arrays.asList(mockReservedHardware);
        when(reservedHardwareService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockReservedList);
        
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);
            
            // Act
            List<ReservedHardwareListEntry> result = operationQueryController.getItemHistory(
                "admin@university.edu", "Dell", "Laptop", (short) 1, Instant.now(), 
                Instant.now().plusSeconds(3600), 10, 0, "name", true
            );
            
            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(reservedHardwareService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        }
    }      @Test
    void testGetItemHistory_AsUser_WithEmailParam_ThrowsException() {
        // Arrange
        when(mockUserDetails.getAuthorities()).thenReturn((Collection) Arrays.asList(mockAuthority));
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);
            
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                operationQueryController.getItemHistory(
                    "other@university.edu", null, null, null, null, null, 10, 0, null, null
                );
            });
        }
    }
      @Test
    void testGetItemHistory_AsUser_WithoutEmailParam_ReturnsUserHistory() {
        // Arrange
        when(mockUserDetails.getUsername()).thenReturn("user@university.edu");
        when(mockUserDetails.getAuthorities()).thenReturn((Collection) Arrays.asList(mockAuthority));
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        
        List<ReservedHardware> mockReservedList = Arrays.asList(mockReservedHardware);
        when(reservedHardwareService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockReservedList);
        
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);
            
            // Act
            List<ReservedHardwareListEntry> result = operationQueryController.getItemHistory(
                null, null, null, null, null, null, 10, 0, null, null
            );
            
            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(reservedHardwareService).get(eq("user@university.edu"), any(), any(), any(), any(), any(), any(), any(), any(), any());
        }
    }
    
    // Tests for getSpaceHistory method
    @Test
    void testGetSpaceHistory_AsAdmin_ReturnsReservedSpaceList() {
        // Arrange
        GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        when(mockUserDetails.getAuthorities()).thenReturn((Collection) Arrays.asList(adminAuthority));
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        
        List<ReservedSpace> mockReservedSpaceList = Arrays.asList(mockReservedSpace);
        when(reservedSpaceService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockReservedSpaceList);
        
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);
            
            // Act
            List<ReservedSpaceListEntry> result = operationQueryController.getSpaceHistory(
                "admin@university.edu", "Computer", "Laboratory", (short) 30, (short) 1, 
                Instant.now(), Instant.now().plusSeconds(3600), 10, 0, "name", true
            );
            
            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(reservedSpaceService).get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        }
    }
    
    @Test
    void testGetSpaceHistory_AsUser_WithEmailParam_ThrowsException() {
        // Arrange
        when(mockUserDetails.getAuthorities()).thenReturn((Collection) Arrays.asList(mockAuthority));
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);
            
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                operationQueryController.getSpaceHistory(
                    "other@university.edu", null, null, null, null, null, null, 10, 0, null, null
                );
            });
        }
    }
    
    @Test
    void testGetSpaceHistory_AsUser_WithoutEmailParam_ReturnsUserHistory() {
        // Arrange
        when(mockUserDetails.getUsername()).thenReturn("user@university.edu");
        when(mockUserDetails.getAuthorities()).thenReturn((Collection) Arrays.asList(mockAuthority));
        when(mockAuthentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        
        List<ReservedSpace> mockReservedSpaceList = Arrays.asList(mockReservedSpace);
        when(reservedSpaceService.get(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
            .thenReturn(mockReservedSpaceList);
        
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(mockSecurityContext);
            
            // Act
            List<ReservedSpaceListEntry> result = operationQueryController.getSpaceHistory(
                null, null, null, null, null, null, null, 10, 0, null, null
            );
            
            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(reservedSpaceService).get(eq("user@university.edu"), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        }
    }
    
}
