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
@Table(name = "parking_slots", uniqueConstraints = {
        @UniqueConstraint(columnNames = "license_plate")
})
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "slot_number", nullable = false, unique = true)
    private String slotNumber;
    @Enumerated(EnumType.STRING)
    private SlotType slotType;
    @Column(name = "is_available", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isAvailable = true;
}
