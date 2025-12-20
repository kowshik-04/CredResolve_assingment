package com.expenses.expense_sharing.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.expenses.expense_sharing.domain.Balance;
import com.expenses.expense_sharing.domain.User;
import com.expenses.expense_sharing.repository.BalanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final BalanceRepository balanceRepository;

    public List<String> getSimplifiedBalances(Long groupId) {

        List<Balance> balances = balanceRepository.findByGroupId(groupId);

        Queue<Map.Entry<User, Long>> creditors = new LinkedList<>();
        Queue<Map.Entry<User, Long>> debtors = new LinkedList<>();

        for (Balance b : balances) {
            long amount = b.getAmountPaise() == null ? 0L : b.getAmountPaise();

            if (amount > 0) {
                creditors.add(Map.entry(b.getUser(), amount));
            } else if (amount < 0) {
                debtors.add(Map.entry(b.getUser(), -amount));
            }
        }

        List<String> result = new ArrayList<>();

        while (!creditors.isEmpty() && !debtors.isEmpty()) {

            var creditor = creditors.poll();
            var debtor = debtors.poll();

            long settlePaise =
                    Math.min(creditor.getValue(), debtor.getValue());

            result.add(
                debtor.getKey().getName()
                + " owes "
                + creditor.getKey().getName()
                + " ₹" + (settlePaise / 100.0)
            );

            if (creditor.getValue() > settlePaise) {
                creditors.add(
                    Map.entry(
                        creditor.getKey(),
                        creditor.getValue() - settlePaise
                    )
                );
            }

            if (debtor.getValue() > settlePaise) {
                debtors.add(
                    Map.entry(
                        debtor.getKey(),
                        debtor.getValue() - settlePaise
                    )
                );
            }
        }

        return result;
    }
}
