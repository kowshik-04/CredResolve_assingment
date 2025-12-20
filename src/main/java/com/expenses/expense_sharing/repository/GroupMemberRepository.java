package com.expenses.expense_sharing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expenses.expense_sharing.domain.GroupMember;
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findByGroupId(Long groupId);
    List<GroupMember> findByUserId(Long userId);
    boolean existsByGroupIdAndUserId(Long groupId, Long userId);
}
