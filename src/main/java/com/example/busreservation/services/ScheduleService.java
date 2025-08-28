package com.example.busreservation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.busreservation.entities.Schedule;
import com.example.busreservation.repos.ScheduleRepository;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findSchedules(String source, String destination) {
        return scheduleRepository.findByRoute_SourceAndRoute_Destination(source, destination);
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}
