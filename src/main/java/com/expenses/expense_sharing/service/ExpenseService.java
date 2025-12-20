package com.expenses.expense_sharing.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.expense_sharing.domain.Balance;
import com.expenses.expense_sharing.domain.Expense;
import com.expenses.expense_sharing.domain.ExpenseSplit;
import com.expenses.expense_sharing.domain.Group;
import com.expenses.expense_sharing.domain.GroupMember;
import com.expenses.expense_sharing.domain.SplitType;
import com.expenses.expense_sharing.domain.User;
import com.expenses.expense_sharing.repository.BalanceRepository;
import com.expenses.expense_sharing.repository.ExpenseRepository;
import com.expenses.expense_sharing.repository.ExpenseSplitRepository;
import com.expenses.expense_sharing.repository.GroupMemberRepository;
import com.expenses.expense_sharing.repository.GroupRepository;
import com.expenses.expense_sharing.strategy.EqualSplitStrategy;
import com.expenses.expense_sharing.strategy.ExactSplitStrategy;
import com.expenses.expense_sharing.strategy.PercentageSplitStrategy;
import com.expenses.expense_sharing.strategy.SplitStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final UserService userService;
    private final BalanceRepository balanceRepository;

    private final EqualSplitStrategy equalSplitStrategy;
    private final ExactSplitStrategy exactSplitStrategy;
    private final PercentageSplitStrategy percentageSplitStrategy;

    @Transactional
    public Expense addExpense(
            String description,
            Double amount,                 // API input
            SplitType splitType,
            Long paidById,
            Long groupId,
            Map<Long, Double> splitDetails // API input
    ) {
        // Convert to paise ONCE
        long totalPaise = Math.round(amount * 100);

        User paidBy = userService.getById(paidById);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<GroupMember> members = groupMemberRepository.findByGroupId(groupId);
        if (members.isEmpty()) {
            throw new RuntimeException("No members found for group");
        }

        List<User> participants = members.stream()
                .map(GroupMember::getUser)
                .toList();

        // Save expense in paise
        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setTotalAmount(totalPaise);   // paise
        expense.setSplitType(splitType);
        expense.setPaidBy(paidBy);
        expense.setGroup(group);

        expense = expenseRepository.save(expense);

        // Apply split strategy (RETURNS PAISE)
        SplitStrategy strategy = getStrategy(splitType);

        Map<User, Long> splitResult =
                strategy.split(totalPaise, participants, splitDetails);

        // Save expense splits (PAISE)
        for (Map.Entry<User, Long> entry : splitResult.entrySet()) {
            expenseSplitRepository.save(
                new ExpenseSplit(
                    null,
                    expense,
                    entry.getKey(),
                    entry.getValue()
                )
            );
        }

        // Update balances (PAISE)
        updateBalances(group, paidBy, splitResult);

        return expense;
    }

    private SplitStrategy getStrategy(SplitType splitType) {
        return switch (splitType) {
            case EQUAL -> equalSplitStrategy;
            case EXACT -> exactSplitStrategy;
            case PERCENTAGE -> percentageSplitStrategy;
        };
    }

    // ---------------- BALANCE ENGINE ----------------

    private void updateBalances(
            Group group,
            User paidBy,
            Map<User, Long> splitResult
    ) {
        for (Map.Entry<User, Long> entry : splitResult.entrySet()) {
            User user = entry.getKey();
            long sharePaise = entry.getValue();

            if (user.getId().equals(paidBy.getId())) {
                continue;
            }

            // User owes money
            updateBalance(group, user, -sharePaise);

            // Payer receives money
            updateBalance(group, paidBy, sharePaise);
        }
    }

    private void updateBalance(Group group, User user, long deltaPaise) {

        Balance balance = balanceRepository
                .findByGroupIdAndUserId(group.getId(), user.getId())
                .orElse(new Balance(null, group, user, 0L));

        balance.setAmountPaise(balance.getAmountPaise() + deltaPaise);

        balanceRepository.save(balance);
    }
}
