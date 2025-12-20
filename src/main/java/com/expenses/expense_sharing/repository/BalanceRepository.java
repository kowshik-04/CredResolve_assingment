package com.expenses.expense_sharing.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenses.expense_sharing.domain.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByGroupIdAndUserId(Long groupId, Long userId);

    List<Balance> findByGroupId(Long groupId);
}
