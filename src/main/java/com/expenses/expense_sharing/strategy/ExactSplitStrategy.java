package com.expenses.expense_sharing.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.expenses.expense_sharing.domain.User;

@Component
public class ExactSplitStrategy implements SplitStrategy {

    @Override
    public Map<User, Long> split(
            Long totalPaise,
            List<User> participants,
            Map<Long, Double> splitDetails
    ) {
        Map<User, Long> result = new HashMap<>();

        for (User user : participants) {
            double amount = splitDetails.get(user.getId());
            result.put(user, Math.round(amount * 100));
        }
        return result;
    }
}