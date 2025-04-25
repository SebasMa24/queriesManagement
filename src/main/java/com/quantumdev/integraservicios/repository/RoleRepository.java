package com.quantumdev.integraservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantumdev.integraservicios.model.ERole;
import com.quantumdev.integraservicios.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, ERole> {

}
