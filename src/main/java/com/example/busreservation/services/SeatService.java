package com.example.busreservation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.busreservation.entities.Bus;
import com.example.busreservation.entities.Seat;
import com.example.busreservation.repos.BusRepository;
import com.example.busreservation.repos.SeatRepository;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BusRepository busRepository;
    
    public Seat addSeat(Seat seat) {
        
        if (seat.getBus() == null || seat.getBus().getBusId() == null) {
            throw new RuntimeException("Bus information is missing in the Seat object.");
        }

        Long busId = seat.getBus().getBusId();
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found with ID: " + busId));
        seat.setBus(bus);
        return seatRepository.save(seat);
    }

    public List<Seat> getSeatsByBusId(Long busId) {
        return seatRepository.findByBus_BusId(busId);
    }

}
