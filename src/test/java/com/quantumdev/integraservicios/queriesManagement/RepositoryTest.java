package com.quantumdev.integraservicios.queriesManagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.quantumdev.integraservicios.database.model.ERole;
import com.quantumdev.integraservicios.database.model.Role;
import com.quantumdev.integraservicios.database.model.User;
import com.quantumdev.integraservicios.database.repository.RoleRepository;
import com.quantumdev.integraservicios.database.repository.UserRepository;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    /** Test logic for inserting a new Role into the database.  */
    @Test
    public void testInsertRoles() {
        for (ERole eRole : ERole.values()) {
            Role role = Role.builder()
                .name(eRole)
                .description(
                    eRole == ERole.ROLE_USER ?
                    "User role with limited access to the system." :
                    "Admin role with full access to the system."
                )
                .build();
            role = this.roleRepository.save(role);

            // Verify that the role was saved successfully
            assert role.getName() != null : "Role ID should not be null after saving";
        }
    }

    /** Test logic for inserting a new AppUser into the database. */
    @Test
    public void testInsertUser() {
        User user = User.builder()
            .email("admin@integraservicios.com")
            .role(Role.builder().name(ERole.ROLE_ADMIN).build())
            .password("admin123")
            .code(20202020123L)
            .name("Admin User")
            .address("123 Main St, City, Country")
            .phone(1234567890L)
            .build();
        
        user = this.userRepository.save(user);

        // Verify that the user was saved successfully
        assert user.getEmail() != null : "User email should not be null after saving";
    }
    
}
