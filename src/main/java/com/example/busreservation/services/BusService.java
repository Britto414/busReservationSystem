package com.example.busreservation.services;


import com.example.busreservation.entities.AppUser;
import com.example.busreservation.entities.Bus;

import java.util.List;
import java.util.Optional;

public interface BusService {
    Bus addBus(Bus bus);

    List<Bus> getAllBus();


}
