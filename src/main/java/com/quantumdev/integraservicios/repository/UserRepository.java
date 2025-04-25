package com.quantumdev.integraservicios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quantumdev.integraservicios.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
}
