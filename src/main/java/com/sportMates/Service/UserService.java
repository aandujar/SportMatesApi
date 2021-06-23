package com.sportMates.Service;

import com.sportMates.Entities.ChangePassword;
import com.sportMates.Entities.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User login(String username, String password);

    User register(User user);

    User getById(int userId);

    Boolean isUsernameInUse(String username);

    Boolean isEmailInUse(String email);

    Boolean updateAvatar(String avatar, int userId);

    Boolean changePassword(ChangePassword changePassword, int userId);
}
