package com.example.busreservation.repos;

import com.example.busreservation.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

public interface BusRepository extends JpaRepository<Bus,Long> {
}
