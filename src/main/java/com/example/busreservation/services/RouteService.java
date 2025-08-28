package com.example.busreservation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.busreservation.entities.Route;
import com.example.busreservation.repos.RouteRepository;


@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;
    
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route addRoute(Route route) {
        return routeRepository.save(route);
    }

    public Route updateRoute(Long routeId, Route route) {
        Route existingRoute = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        existingRoute.setSource(route.getSource());
        existingRoute.setDestination(route.getDestination());
        existingRoute.setDistance(route.getDistance());

        return routeRepository.save(existingRoute);
    }
}
