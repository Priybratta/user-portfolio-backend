package com.portfolio.backend.service.impl;

import com.portfolio.backend.entity.AuthProvider;
import com.portfolio.backend.entity.User;
import com.portfolio.backend.repository.UserRepository;
import com.portfolio.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findOrCreateUser(String email, String name, String photoUrl) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .email(email)
                                .fullName(name)
                                .photoUrl(photoUrl)
                                .authProvider(AuthProvider.GOOGLE)
                                .enabled(true)
                                .createdAt(LocalDateTime.now())
                                .build()
                ));
    }
}
