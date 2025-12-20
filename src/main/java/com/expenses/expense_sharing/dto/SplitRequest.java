package com.expenses.expense_sharing.dto;

import lombok.Data;

@Data
public class SplitRequest {
    private Long userId;
    private Long amount;   // amount in paise
}
