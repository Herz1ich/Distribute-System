package org.example.springbootrestapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage")
public class UsageEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private double kwh;
    private LocalDateTime timestamp;

    // getters and setters
}

