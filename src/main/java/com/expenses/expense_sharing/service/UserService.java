package com.expenses.expense_sharing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.expenses.expense_sharing.domain.User;
import com.expenses.expense_sharing.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor   // IMPORTANT
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
