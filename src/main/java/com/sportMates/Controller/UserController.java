package com.sportMates.Controller;

import com.sportMates.Entities.User;
import com.sportMates.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
