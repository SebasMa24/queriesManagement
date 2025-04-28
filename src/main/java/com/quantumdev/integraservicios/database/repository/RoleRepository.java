package com.quantumdev.integraservicios.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantumdev.integraservicios.database.model.ERole;
import com.quantumdev.integraservicios.database.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, ERole> {

}
