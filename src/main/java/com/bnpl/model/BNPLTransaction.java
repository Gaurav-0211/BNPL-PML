package com.bnpl.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bnpl_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BNPLTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private Integer tenureMonths; // e.g., 3, 6, 9, 12

    private Double emiAmount;

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE, CLOSED, OVERDUE

    private LocalDate createdAt;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // customer

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EMI> emis;

    public enum Status {
        ACTIVE, CLOSED, OVERDUE
    }
}
