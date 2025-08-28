package com.example.busreservation.repos;

import com.example.busreservation.entities.Bus;
import com.example.busreservation.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByRoute_SourceAndRoute_Destination(String source, String destination);
    Optional<Schedule> findById(Long id);
}
