package com.expenses.expense_sharing.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.expenses.expense_sharing.domain.Balance;
import com.expenses.expense_sharing.dto.SettlementDTO;
import com.expenses.expense_sharing.repository.BalanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final BalanceRepository balanceRepository;

    public List<SettlementDTO> getSettlements(Long groupId) {

        List<Balance> balances = balanceRepository.findByGroupId(groupId);

        Queue<Balance> creditors = new LinkedList<>();
        Queue<Balance> debtors = new LinkedList<>();

        // Separate creditors and debtors (PAISE)
        for (Balance b : balances) {
            if (b.getAmountPaise() > 0) {
                creditors.add(b);
            } else if (b.getAmountPaise() < 0) {
                debtors.add(b);
            }
        }

        List<SettlementDTO> settlements = new ArrayList<>();

        while (!creditors.isEmpty() && !debtors.isEmpty()) {

            Balance creditor = creditors.poll();
            Balance debtor = debtors.poll();

            long settlePaise =
                    Math.min(
                        creditor.getAmountPaise(),
                        -debtor.getAmountPaise()
                    );

            settlements.add(
                new SettlementDTO(
                    debtor.getUser().getId(),
                    creditor.getUser().getId(),
                    settlePaise / 100.0   // convert ONLY for API
                )
            );

            // Update balances in memory (PAISE)
            creditor.setAmountPaise(
                creditor.getAmountPaise() - settlePaise
            );
            debtor.setAmountPaise(
                debtor.getAmountPaise() + settlePaise
            );

            if (creditor.getAmountPaise() > 0) creditors.add(creditor);
            if (debtor.getAmountPaise() < 0) debtors.add(debtor);
        }

        return settlements;
    }
}
