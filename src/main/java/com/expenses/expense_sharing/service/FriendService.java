package com.expenses.expense_sharing.service;

import com.expenses.expense_sharing.domain.Friend;
import com.expenses.expense_sharing.domain.User;
import com.expenses.expense_sharing.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserService userService;

    public void addFriend(Long userId, Long friendId) {
        User user = userService.getById(userId);
        User friend = userService.getById(friendId);

        // user -> friend
        friendRepository.save(new Friend(null, user, friend));
        // friend -> user
        friendRepository.save(new Friend(null, friend, user));
    }
}
