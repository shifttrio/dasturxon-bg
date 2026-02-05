package org.example.tezkor.enums;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullname;

    @Column(unique = true)
    private String phone;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active = true;
}
