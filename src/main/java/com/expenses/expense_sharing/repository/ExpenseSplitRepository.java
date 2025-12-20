package com.expenses.expense_sharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenses.expense_sharing.domain.ExpenseSplit;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
}
