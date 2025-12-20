package com.expenses.expense_sharing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expenses.expense_sharing.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
