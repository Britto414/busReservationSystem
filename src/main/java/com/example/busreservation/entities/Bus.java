package com.example.busreservation.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "buses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;

    @Column(nullable = false, unique = true)
    private String busNumber;

    private String type; // AC/Non-AC, Sleeper, etc.

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;
}

