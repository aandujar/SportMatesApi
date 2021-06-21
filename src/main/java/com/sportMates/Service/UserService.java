package com.sportMates.Service;

import com.sportMates.Entities.User;

public interface UserService {

    User login(String username, String password);

    User register(User user);

    User getById(int userId);

    Boolean isUsernameInUse(String username);

    Boolean isEmailInUse(String email);
}
