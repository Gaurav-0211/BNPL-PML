package com.bnpl.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "emis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EMI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDate dueDate;

    private LocalDate paidDate;

    @Enumerated(EnumType.STRING)
    private Status status; // PENDING, PAID, OVERDUE

    // Relationships
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private BNPLTransaction transaction;

    public enum Status {
        PENDING, PAID, OVERDUE
    }
}
