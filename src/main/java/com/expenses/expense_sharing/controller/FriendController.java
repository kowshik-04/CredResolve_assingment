package com.expenses.expense_sharing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.expense_sharing.service.FriendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping
    public String addFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        friendService.addFriend(userId, friendId);
        return "Friend added successfully";
    }
}
