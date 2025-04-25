package com.quantumdev.integraservicios.queriesManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.quantumdev.integraservicios.model.ERole;
import com.quantumdev.integraservicios.model.Role;
import com.quantumdev.integraservicios.model.User;
import com.quantumdev.integraservicios.repository.RoleRepository;
import com.quantumdev.integraservicios.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories("com.quantumdev.integraservicios.repository")
@EntityScan("com.quantumdev.integraservicios.model")
public class QueriesManagementApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(QueriesManagementApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		Role role = new Role();
		role.setName(ERole.ROLE_USER);
		role.setDescription("User role with limited access to the system.");

		roleRepository.save(role);

		User user = new User();
		user.setEmail("user@email.com");
		user.setRole(role);
		user.setPassword("password123");
		user.setCode(20202020008L);
		user.setName("John Doe");
		user.setAddress("123 Main St, City, Country");
		user.setPhone(1234567890L);

		this.userRepository.save(user);
	}

}
