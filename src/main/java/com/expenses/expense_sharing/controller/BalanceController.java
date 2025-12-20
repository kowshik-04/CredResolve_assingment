package com.expenses.expense_sharing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.expense_sharing.service.BalanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/{groupId}")
    public List<String> getBalances(@PathVariable Long groupId) {
        return balanceService.getSimplifiedBalances(groupId);
    }
}
