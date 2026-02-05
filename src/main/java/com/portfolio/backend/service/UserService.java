package com.portfolio.backend.service;

import com.portfolio.backend.entity.User;

public interface UserService {

    User findOrCreateUser(
            String email,
            String name,
            String photoUrl
    );
}
