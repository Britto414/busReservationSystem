package com.example.busreservation.controller;


import com.example.busreservation.entities.Schedule;

import com.example.busreservation.services.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    
    @PostMapping
    public ResponseEntity<Schedule> addSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.ok(scheduleService.addSchedule(schedule));
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules(
            @RequestParam String source,
            @RequestParam String destination) {
        return ResponseEntity.ok(scheduleService.findSchedules(source, destination));
    }
}
