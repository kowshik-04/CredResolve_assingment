package com.expenses.expense_sharing.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Long totalAmount;
    private Long totalAmountPaise;


    @Enumerated(EnumType.STRING)
    private SplitType splitType;

    @ManyToOne
    private User paidBy;

    @ManyToOne
    private Group group;

    private LocalDateTime createdAt = LocalDateTime.now();
}
