package org.example.tezkor.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactRequestDto {

    private String phoneNumber;
    private String firstName;
    private String lastName;
}