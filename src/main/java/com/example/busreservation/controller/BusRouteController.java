package com.example.busreservation.controller;


import com.example.busreservation.entities.Route;
import com.example.busreservation.models.ResponseModel;
import com.example.busreservation.services.RouteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class BusRouteController {

    @Autowired
    private RouteService busRouteService;

    @PostMapping("/add")
    public ResponseModel<Route> addRoute(@RequestBody Route busRoute) {
        final Route busRoute1 = busRouteService.addRoute(busRoute);
        return new ResponseModel<>(HttpStatus.OK.value(), "Route saved", busRoute1);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Route>> getAllRoutes() {
        return ResponseEntity.ok(busRouteService.getAllRoutes());
    }

    @PutMapping("/update/{routeId}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long routeId, @RequestBody Route busRoute) {
        Route route = busRouteService.updateRoute(routeId, busRoute);
        return ResponseEntity.ok(route);
    }

}
