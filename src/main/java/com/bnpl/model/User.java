package com.bnpl.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String phone;

    private Integer creditScore; // used for risk assessment

    @Enumerated(EnumType.STRING)
    private Role role;  // CUSTOMER, MERCHANT, ADMIN

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BNPLTransaction> transactions;

    public enum Role {
        CUSTOMER, MERCHANT, ADMIN
    }
}
