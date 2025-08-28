package com.example.busreservation.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.busreservation.entities.Operator;
import com.example.busreservation.repos.OperatorRepository;

@Service
public class OperatorService {
    @Autowired
    private OperatorRepository operatorRepository;

    public Operator addOperator(Operator operator) {
        return operatorRepository.save(operator);
    }
    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }

    
}
