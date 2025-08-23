package com.example.busreservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.busreservation.entities.AppUser;

public interface AppuserRepository extends JpaRepository<AppUser,Long>{
    Optional<AppUser> findByUserName(String userName);
}
