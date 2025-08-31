package com.example.busreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.busreservation.entities.Operator;
import com.example.busreservation.services.OperatorService;

@RequestMapping("/api/admin/operators")
@RestController
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @PostMapping()
    public Operator add(@RequestBody Operator operator) {
        return operatorService.addOperator(operator);
    }
}
