package com.quantumdev.integraservicios.queriesManagement.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantumdev.integraservicios.database.model.ERole;
import com.quantumdev.integraservicios.database.model.ReservedHardware;
import com.quantumdev.integraservicios.database.model.ReservedSpace;
import com.quantumdev.integraservicios.queriesManagement.Service.ReservedHardwareService;
import com.quantumdev.integraservicios.queriesManagement.Service.ReservedSpaceService;
import com.quantumdev.integraservicios.queriesManagement.Service.SpaceService;
import com.quantumdev.integraservicios.queriesManagement.Service.StoredHardwareService;
import com.quantumdev.integraservicios.queriesManagement.response.ReservedHardwareListEntry;
import com.quantumdev.integraservicios.queriesManagement.response.ReservedSpaceListEntry;
import com.quantumdev.integraservicios.queriesManagement.response.SpaceListEntry;
import com.quantumdev.integraservicios.queriesManagement.response.StoredHardwareListEntry;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.time.Instant;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * QueryController is a REST controller that handles queries related to hardware and space reservations.
 * It provides endpoints to retrieve available items and their history based on various filters.
 * @author Nicolás Sabogal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/operations")
public class OperationQueryController {

    /** Service for managing space-related queries. */
    private final SpaceService spaceService;

    /** Service for managing stored hardware-related queries. */
    private final StoredHardwareService storedHardwareService;

    /** Service for managing reserved hardware-related queries. */
    private final ReservedHardwareService reservedHardwareService;

    /** Service for managing reserved space-related queries. */
    private final ReservedSpaceService reservedSpaceService;

    /**
     * Retrieves a reserved hardware item by its ID.
     * @param id  ID of the reserved hardware item.
     * @return    ReservedHardware object if found, null otherwise.
     */
    @GetMapping("/hardware/{id}")
    public ReservedHardware getReservedHardware(@PathVariable Long id) {
        return this.reservedHardwareService.getById(id);
    }

    /**
     * Retrieves a reserved space by its ID.
     * @param id  ID of the reserved space.
     * @return    ReservedHardware object if found, null otherwise.
     */
    @GetMapping("/space/{id}")
    public ReservedSpace getReservedSpace(@PathVariable Long id) {
        return this.reservedSpaceService.getById(id);
    }

    /**
     * Retrieves available hardware items based on the provided filters.
     * @param nameLike  Partial name of the hardware item to search for (optional).
     * @param type      Type of the hardware item (optional).
     * @param building  Building number where the hardware is located (optional).
     * @param startDate Start date for the reservation period (required if getAll is false).
     * @param endDate   End date for the reservation period (required if getAll is false).
     * @param getAll    Flag to indicate if all spaces should be retrieved or only available ones (assumed false).
     * @param qSize     Number of items to retrieve per page.
     * @param qPage     Page number to retrieve.
     * @param orderBy   Field to order the results by (optional).
     * @param ascOrder  Flag to indicate if the results should be ordered in ascending order (assumed true).
     * @return          List of available spaces matching the filters.
     */
    @GetMapping("/hardware/availability")
    public List<StoredHardwareListEntry> getAvailableItems(
        @RequestParam(required = false) String nameLike,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Short building,
        @RequestParam(required = false) Instant startDate,
        @RequestParam(required = false) Instant endDate,
        @RequestParam(required = false) Boolean getAll,
        @RequestParam(defaultValue = "10") Integer qSize,
        @RequestParam(defaultValue = "0") Integer qPage,
        @RequestParam(required = false) String orderBy,
        @RequestParam(required = false) Boolean descOrder
    ) {
        return this.storedHardwareService.get(
                nameLike,
                type,
                building,
                startDate,
                endDate,
                getAll,
                qSize,
                qPage,
                orderBy,
                descOrder
            )
            .stream()
            .map(StoredHardwareListEntry::from)
            .toList();
    };

    /**
     * Retrieves available spaces based on the provided filters.
     * @param nameLike  Partial name of the space to search for (optional).
     * @param type      Type of the space (optional).
     * @param capacity  Minimum capacity of the space (optional).
     * @param building  Building number where the space is located (optional).
     * @param startDate Start date for the reservation period (required if getAll is false).
     * @param endDate   End date for the reservation period (required if getAll is false).
     * @param getAll    Flag to indicate if all spaces should be retrieved or only available ones (assumed false).
     * @param qSize     Number of items to retrieve per page.
     * @param qPage     Page number to retrieve.
     * @param orderBy   Field to order the results by (optional).
     * @param ascOrder  Flag to indicate if the results should be ordered in ascending order (assumed true).
     * @return          List of available spaces matching the filters.
     */
    @GetMapping("/space/availability")
    public List<SpaceListEntry> getAvailableSpaces(
        @RequestParam(required = false) String nameLike,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Short capacity,
        @RequestParam(required = false) Short building,
        @RequestParam(required = false) Instant startDate,
        @RequestParam(required = false) Instant endDate,
        @RequestParam(required = false) Boolean getAll,
        @RequestParam(defaultValue = "10") Integer qSize,
        @RequestParam(defaultValue = "0") Integer qPage,
        @RequestParam(required = false) String orderBy,
        @RequestParam(required = false) Boolean ascOrder
    ) {
        List<SpaceListEntry> list = this.spaceService.get(
                nameLike,
                type,
                capacity,
                building,
                startDate,
                endDate,
                getAll,
                qSize,
                qPage,
                orderBy,
                ascOrder
            )
            .stream()
            .map(SpaceListEntry::from)
            .toList();
        
        return list;
    };

    /**
     * Retrieves the history of reserved hardware items based on the provided filters.
     * @param email     Email of the user making the request (optional for ADMIN, required).
     * @param nameLike  Partial name of the hardware item to search for (optional).
     * @param type      Type of the hardware item (optional).
     * @param building  Building number where the hardware was located (optional).
     * @param startDate Start date for the reservation period (optional).
     * @param endDate   End date for the reservation period (optional).
     * @param qSize     Number of reservations to retrieve per page.
     * @param qPage     Page number to retrieve.
     * @param orderBy   Field to order the results by (optional).
     * @param ascOrder  Flag to indicate if the results should be ordered in ascending order (assumed true).
     * @return          List of reserved hardware items matching the filters.
     */
    @GetMapping("/hardware")
    public List<ReservedHardwareListEntry> getItemHistory(
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String nameLike,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Short building,
        @RequestParam(required = false) Instant startDate,
        @RequestParam(required = false) Instant endDate,
        @RequestParam(defaultValue = "10") Integer qSize,
        @RequestParam(defaultValue = "0") Integer qPage,
        @RequestParam(required = false) String orderBy,
        @RequestParam(required = false) Boolean ascOrder
    ) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(ERole.ROLE_ADMIN.name()))
           ) {
            if (email != null)
                throw new IllegalArgumentException("User not authorized to access this resource.");
            email = user.getUsername();
        }

        return this.reservedHardwareService.get(
                email,
                nameLike,
                type,
                building,
                startDate,
                endDate,
                qSize,
                qPage,
                orderBy,
                ascOrder
            )
            .stream()
            .map(ReservedHardwareListEntry::from)
            .toList();
    };

    /**
     * Retrieves the history of reserved spaces based on the provided filters.
     * @param email     Email of the user making the request (optional for ADMIN, required).
     * @param nameLike  Partial name of the space to search for (optional).
     * @param type      Type of the space (optional).
     * @param capacity  Minimum capacity of the space (optional).
     * @param building  Building number where the space was located (optional).
     * @param startDate Start date for the reservation period (optional).
     * @param endDate   End date for the reservation period (optional).
     * @param qSize     Number of reservations to retrieve per page.
     * @param qPage     Page number to retrieve.
     * @param orderBy   Field to order the results by (optional).
     * @param ascOrder  Flag to indicate if the results should be ordered in ascending order (assumed true).
     * @return          List of reserved spaces matching the filters.
     */
    @GetMapping("/space")
    public List<ReservedSpaceListEntry> getSpaceHistory(
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String nameLike,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Short capacity,
        @RequestParam(required = false) Short building,
        @RequestParam(required = false) Instant startDate,
        @RequestParam(required = false) Instant endDate,
        @RequestParam(defaultValue = "10") Integer qSize,
        @RequestParam(defaultValue = "0") Integer qPage,
        @RequestParam(required = false) String orderBy,
        @RequestParam(required = false) Boolean ascOrder
    ) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!user
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(ERole.ROLE_ADMIN.name()))
           ) {
            if (email != null)
                throw new IllegalArgumentException("User not authorized to access this resource.");
            email = user.getUsername();
        }

        return this.reservedSpaceService.get(
                email,
                nameLike,
                type,
                capacity,
                building,
                startDate,
                endDate,
                qSize,
                qPage,
                orderBy,
                ascOrder
            )
            .stream()
            .map(ReservedSpaceListEntry::from)
            .toList();
    };

}
