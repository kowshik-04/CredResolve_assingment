package com.expenses.expense_sharing.repository;

import com.expenses.expense_sharing.domain.Friend;
import com.expenses.expense_sharing.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUser(User user);
}
