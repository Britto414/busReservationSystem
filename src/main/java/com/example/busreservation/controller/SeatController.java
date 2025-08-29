package com.example.busreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.busreservation.services.SeatService;
import com.example.busreservation.entities.Seat; // Ensure this is the correct package for the Seat class
import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("/bus/{busId}")
    public ResponseEntity<List<Seat>> getSeatsByBusId(@PathVariable Long busId) {
        return ResponseEntity.ok(seatService.getSeatsByBusId(busId));
    }


    @PostMapping
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) {
        return ResponseEntity.ok(seatService.addSeat(seat));
    }
}
