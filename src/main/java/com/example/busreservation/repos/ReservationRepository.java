package com.example.busreservation.repos;

import com.example.busreservation.entities.Schedule;
import com.example.busreservation.entities.Reservation;
import com.example.busreservation.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Get all reservations by user
    List<Reservation> findByUser_UserId(Long userId);

    // Check if a seat is already booked for a schedule
    boolean existsByScheduleAndSeatAndStatus(Schedule schedule, Seat seat, String status);

    boolean existsBySeatSeatIdAndScheduleScheduleId(long seatId, long scheduleId);

    List<Reservation> findByUserUserId(Long userId);

    Reservation findBySeatSeatIdAndScheduleScheduleId(Long seatId, Long scheduleId);
}
