package com.sportMates.Controller;

import com.sportMates.Entities.ChangePassword;
import com.sportMates.Entities.User;
import com.sportMates.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @PostMapping("/register")
    public Object login(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/email")
    public Object isEmailInUse(@RequestParam String email) {
        return userService.isEmailInUse(email);
    }

    @GetMapping("/username")
    public Object isUsernameInUse(@RequestParam String username) {
        return userService.isUsernameInUse(username);
    }

    @PutMapping("/avatar/{userId}")
    public Object changeAvatar(@RequestBody String avatar, @PathVariable int userId) {
        return userService.updateAvatar(avatar, userId);
    }

    @PutMapping("/password/{userId}")
    public Object changePassword(@RequestBody ChangePassword changePassword, @PathVariable int userId) {
        return userService.changePassword(changePassword, userId);
    }
}
