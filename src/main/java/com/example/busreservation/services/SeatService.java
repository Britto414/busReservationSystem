package com.example.busreservation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.busreservation.entities.Seat;
import com.example.busreservation.repos.SeatRepository;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;


    public Seat addSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByBusId(Long busId) {
        return seatRepository.findByBus_BusId(busId);
    }

}
