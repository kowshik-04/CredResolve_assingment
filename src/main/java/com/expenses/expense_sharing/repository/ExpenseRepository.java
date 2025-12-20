package com.expenses.expense_sharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenses.expense_sharing.domain.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
