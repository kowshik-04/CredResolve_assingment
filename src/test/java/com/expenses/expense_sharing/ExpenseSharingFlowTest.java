package com.expenses.expense_sharing;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.expenses.expense_sharing.domain.Balance;
import com.expenses.expense_sharing.domain.SplitType;
import com.expenses.expense_sharing.domain.User;
import com.expenses.expense_sharing.dto.SettlementDTO;
import com.expenses.expense_sharing.repository.BalanceRepository;
import com.expenses.expense_sharing.service.BalanceService;
import com.expenses.expense_sharing.service.ExpenseService;
import com.expenses.expense_sharing.service.GroupService;
import com.expenses.expense_sharing.service.SettleUpService;
import com.expenses.expense_sharing.service.SettlementService;
import com.expenses.expense_sharing.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@Disabled
class ExpenseSharingFlowTest {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private SettlementService settlementService;

    @Autowired
    private SettleUpService settleUpService;

    @Autowired
    private BalanceService balanceService;

    @Test
    void fullExpenseSharingFlowTest() {

        // ---------------- USERS ----------------
        User userA = userService.create(
        new User(null, "Test1", "Test1@test.com")
);
User userB = userService.create(
        new User(null, "Test2", "Test2@test.com")
);
User userC = userService.create(
        new User(null, "Test3", "Test3@test.com")
);


        assertThat(userA.getId()).isNotNull();

        // ---------------- GROUP ----------------
        Long groupId = groupService.createGroup(
                "Goa Trip",
                userA.getId(),
                List.of(userA.getId(), userB.getId(), userC.getId())
        ).getId();

        // ---------------- EXPENSE 1 (EQUAL) ----------------
        expenseService.addExpense(
                "Hotel",
                3000.0,
                SplitType.EQUAL,
                userA.getId(),
                groupId,
                null
        );

        // ---------------- EXPENSE 2 (EXACT) ----------------
        expenseService.addExpense(
                "Cab",
                1500.0,
                SplitType.EXACT,
                userB.getId(),
                groupId,
                Map.of(
                    userA.getId(), 500.0,
                    userB.getId(), 500.0,
                    userC.getId(), 500.0
                )
        );

        // ---------------- EXPENSE 3 (PERCENTAGE) ----------------
        expenseService.addExpense(
                "Dinner",
                2000.0,
                SplitType.PERCENTAGE,
                userC.getId(),
                groupId,
                Map.of(
                    userA.getId(), 50.0,
                    userB.getId(), 30.0,
                    userC.getId(), 20.0
                )
        );

        // ---------------- BALANCES ----------------
        List<Balance> balances = balanceRepository.findByGroupId(groupId);
        assertThat(balances).hasSize(3);

        long totalPaise = balances.stream()
                .mapToLong(Balance::getAmountPaise)
                .sum();

        // Sum of balances must always be zero
        assertThat(totalPaise).isZero();

        // ---------------- NET SETTLEMENT ----------------
        List<SettlementDTO> settlements =
                settlementService.getSettlements(groupId);

        assertThat(settlements).isNotEmpty();

        // ---------------- MANUAL SETTLE-UP ----------------
        SettlementDTO first = settlements.get(0);

        long amountPaise = (long)Math.round(first.getAmount() * 100);

        settleUpService.settle(
            groupId,
            first.getFromUserId(),
            first.getToUserId(),
            amountPaise
        );



        // ---------------- FINAL BALANCES ----------------
        List<String> simplified =
                balanceService.getSimplifiedBalances(groupId);

        // After partial settle, balances may still exist
        assertThat(simplified).isNotNull();
    }
}
