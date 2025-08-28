package com.example.busreservation.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.busreservation.entities.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    
}
