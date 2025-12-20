package com.expenses.expense_sharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenses.expense_sharing.domain.Settlement;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
