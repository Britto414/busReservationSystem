package com.example.busreservation.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.busreservation.entities.Reservation;
import com.example.busreservation.entities.Schedule;
import com.example.busreservation.entities.Seat;
import com.example.busreservation.entities.User;
import com.example.busreservation.repos.ReservationRepository;
import com.example.busreservation.repos.ScheduleRepository;
import com.example.busreservation.repos.SeatRepository;
import com.example.busreservation.repos.UserRepository;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private SeatRepository seatRepository;

    public Reservation add(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUserUserId(userId);
    }

    public Reservation bookSeat(Long userId, Long scheduleId, Long seatId) {
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check Schedule
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // 3. Check Seat
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        // 4. Prevent double booking
        if (reservationRepository.existsBySeatSeatIdAndScheduleScheduleId(seatId, scheduleId)) {
            throw new RuntimeException("Seat already booked for this schedule");
        }

        // 5. Create Reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSchedule(schedule);
        reservation.setSeat(seat);
        reservation.setBookingDate(LocalDate.now().atStartOfDay());
        reservation.setStatus(Reservation.Status.BOOKED);

        return reservationRepository.save(reservation);
    }

    public Reservation cancelReservation(Long bookingId) {
        Reservation reservation = reservationRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        reservation.setStatus(Reservation.Status.CANCELLED);
        return reservationRepository.save(reservation);
    }
}
