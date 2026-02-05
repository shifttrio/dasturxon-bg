package org.example.tezkor.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponseDto {

    private Long id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isNewContact;
}