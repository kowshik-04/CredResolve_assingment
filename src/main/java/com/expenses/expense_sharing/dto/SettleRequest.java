package com.expenses.expense_sharing.dto;

import lombok.Data;

@Data
public class SettleRequest {
    private Long groupId;
    private Long fromUserId;
    private Long toUserId;
    private Long amount;
}
