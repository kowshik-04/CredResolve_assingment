package com.expenses.expense_sharing.strategy;

import java.util.List;
import java.util.Map;

import com.expenses.expense_sharing.domain.User;

// public interface SplitStrategy {

//     Map<User, Double> split(
//             Double totalAmount,
//             List<User> participants,
//             Map<Long, Double> splitDetails
//     );
// }


public interface SplitStrategy {
    Map<User, Long> split(
        Long totalPaise,
        List<User> participants,
        Map<Long, Double> splitDetails
    );
}
