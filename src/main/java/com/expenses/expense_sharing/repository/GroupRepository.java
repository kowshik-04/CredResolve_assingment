package com.expenses.expense_sharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenses.expense_sharing.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByNameAndCreatedById(String name, Long createdById);
}
