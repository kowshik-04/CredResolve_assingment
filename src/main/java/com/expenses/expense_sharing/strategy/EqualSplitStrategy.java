package com.expenses.expense_sharing.strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.expenses.expense_sharing.domain.User;

@Component
public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public Map<User, Long> split(
            Long totalPaise,
            List<User> participants,
            Map<Long, Double> splitDetails
    ) {
        int n = participants.size();
        long perHead = totalPaise / n;

        Map<User, Long> result = new HashMap<>();
        for (User user : participants) {
            result.put(user, perHead);
        }
        return result;
    }
}

