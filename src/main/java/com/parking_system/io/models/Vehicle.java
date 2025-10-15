package com.parking_system.io.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles", uniqueConstraints = {
        @UniqueConstraint(columnNames = "license_plate")
})
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;
    @Column(name = "owner_name", nullable = false)
    private String ownerName;
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;
}
