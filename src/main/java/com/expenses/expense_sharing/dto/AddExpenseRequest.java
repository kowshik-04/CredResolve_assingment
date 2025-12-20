package com.expenses.expense_sharing.dto;

import java.util.List;

import com.expenses.expense_sharing.domain.SplitType;

import lombok.Data;

@Data
public class AddExpenseRequest {
    private String description;
    private Long amount;          // amount in paise
    private SplitType splitType;
    private Long paidById;
    private Long groupId;
    private List<SplitRequest> splits;
}
