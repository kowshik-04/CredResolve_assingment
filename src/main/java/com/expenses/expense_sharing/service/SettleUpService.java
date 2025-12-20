package com.expenses.expense_sharing.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.expense_sharing.domain.Balance;
import com.expenses.expense_sharing.domain.Group;
import com.expenses.expense_sharing.domain.Settlement;
import com.expenses.expense_sharing.domain.User;
import com.expenses.expense_sharing.repository.BalanceRepository;
import com.expenses.expense_sharing.repository.GroupRepository;
import com.expenses.expense_sharing.repository.SettlementRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettleUpService {

    private final BalanceRepository balanceRepository;
    private final SettlementRepository settlementRepository;
    private final GroupRepository groupRepository;
    private final UserService userService;

    @Transactional
    public void settle(
            Long groupId,
            Long fromUserId,
            Long toUserId,
            Long amount   // API input (rupees)
    ) {
        // Convert once
        long amountPaise = Math.round(amount * 100);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User fromUser = userService.getById(fromUserId);
        User toUser = userService.getById(toUserId);

        Balance fromBalance = balanceRepository
                .findByGroupIdAndUserId(groupId, fromUserId)
                .orElseThrow(() -> new RuntimeException("No balance found"));

        Balance toBalance = balanceRepository
                .findByGroupIdAndUserId(groupId, toUserId)
                .orElseThrow(() -> new RuntimeException("No balance found"));

        // fromUser must owe money (negative balance)
        if (fromBalance.getAmountPaise() + amountPaise > 0) {
            throw new RuntimeException("Settlement exceeds owed amount");
        }

        // Update balances (PAISE)
        fromBalance.setAmountPaise(
                fromBalance.getAmountPaise() + amountPaise
        );

        toBalance.setAmountPaise(
                toBalance.getAmountPaise() - amountPaise
        );

        balanceRepository.save(fromBalance);
        balanceRepository.save(toBalance);

        // Persist settlement record (PAISE)
        Settlement settlement = new Settlement();
        settlement.setGroup(group);
        settlement.setFromUser(fromUser);
        settlement.setToUser(toUser);
        settlement.setAmountPaise(amountPaise);

        settlementRepository.save(settlement);
    }
}
