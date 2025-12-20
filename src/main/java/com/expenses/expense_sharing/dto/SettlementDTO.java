package com.expenses.expense_sharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettlementDTO {
    private Long fromUserId;
    private Long toUserId;
    private Double amount;
}
