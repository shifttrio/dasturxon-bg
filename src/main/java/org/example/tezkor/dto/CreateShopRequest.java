package org.example.tezkor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShopRequest {

    private String name;
    private String address;
    private String description;
    private String estimatedDeliveryTime;

    private String adminFullname;
    private String adminPhone;
    private String adminPassword;

    private String imageUrl; // URL orqali rasm
}
