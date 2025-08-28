package com.example.busreservation.services;

import com.example.busreservation.entities.Bus;
import com.example.busreservation.entities.Operator;
import com.example.busreservation.repos.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    // Add a new bus
    public Bus addBus(Bus bus) {
        return busRepository.save(bus);
    }

    // Get all buses
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    // Update a bus
    public Bus updateBus(Long busId, Bus updatedBus) {
        return busRepository.findById(busId).map(bus -> {
            bus.setBusNumber(updatedBus.getBusNumber());
            bus.setCapacity(updatedBus.getCapacity());
            bus.setType(updatedBus.getType());
            bus.setOperator(updatedBus.getOperator());
            return busRepository.save(bus);
        }).orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + busId));
    }

    // Delete a bus by ID
    public void deleteBus(Long busId) {
        if (!busRepository.existsById(busId)) {
            throw new IllegalArgumentException("Bus not found with ID: " + busId);
        }
        busRepository.deleteById(busId);
    }

    public Operator getOperatorByBusId(Long busId) {
        Optional<Bus> busOpt = busRepository.findById(busId);
        if (busOpt.isPresent()) {
            return busOpt.get().getOperator();
        } else {
            throw new RuntimeException("Bus not found");
        }
    }
}