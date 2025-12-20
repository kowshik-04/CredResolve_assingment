package com.expenses.expense_sharing.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.expenses.expense_sharing.dto.SettleRequest;
import com.expenses.expense_sharing.dto.SettlementDTO;
import com.expenses.expense_sharing.service.SettleUpService;
import com.expenses.expense_sharing.service.SettlementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;
    private final SettleUpService settleUpService;

    
    @GetMapping("/{groupId}")
    public List<SettlementDTO> getSettlements(@PathVariable Long groupId) {
        return settlementService.getSettlements(groupId);
    }

   
    @PostMapping("/settle")
    public void settle(@RequestBody SettleRequest request) {
        settleUpService.settle(
                request.getGroupId(),
                request.getFromUserId(),
                request.getToUserId(),
                request.getAmount()
        );
    }
}
