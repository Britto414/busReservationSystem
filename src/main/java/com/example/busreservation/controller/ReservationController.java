package com.example.busreservation.controller;

import com.example.busreservation.entities.Reservation;
import com.example.busreservation.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    

    @PostMapping("/book")
    public ResponseEntity<Reservation> bookSeat(
            @RequestParam Long userId,
            @RequestParam Long scheduleId,
            @RequestParam Long seatId) {

        return ResponseEntity.ok(reservationService.bookSeat(userId, scheduleId, seatId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable Long userId) {
        return ResponseEntity.ok(reservationService.getReservationsByUser(userId));
    }

    @PostMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long bookingId) {
        reservationService.cancelReservation(bookingId);
        return ResponseEntity.ok("Reservation cancelled successfully!");
    }
}
