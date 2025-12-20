package com.expenses.expense_sharing.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.expense_sharing.domain.Expense;
import com.expenses.expense_sharing.domain.SplitType;
import com.expenses.expense_sharing.service.ExpenseService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public Expense add(@RequestBody CreateExpenseRequest request) {
        return expenseService.addExpense(
                request.getDescription(),
                request.getAmount(),
                request.getSplitType(),
                request.getPaidBy(),
                request.getGroupId(),
                request.getSplitDetails()
        );
    }
}

@Data
class CreateExpenseRequest {
    private String description;
    private Double amount;
    private SplitType splitType;
    private Long paidBy;
    private Long groupId;
    private Map<Long, Double> splitDetails;
}
