package org.example.tezkor.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shops")
@Getter
@Setter
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String estimatedDeliveryTime;

    @Column(name = "image")
    private String image; // endi URL bo'ladi


    // 🔥 LOKATSIYA
    private Double latitude;   // masalan: 41.2995
    private Double longitude;  // masalan: 69.2401
}
