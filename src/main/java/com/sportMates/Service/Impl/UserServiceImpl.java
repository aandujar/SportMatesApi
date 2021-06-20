package com.sportMates.Service.Impl;

import com.sportMates.Entities.User;
import com.sportMates.Exception.BadRequestException;
import com.sportMates.Repository.UserRepository;
import com.sportMates.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new BadRequestException("datos incorrectos");
        }

        if(!this.isSameEncriptedPassword(password, user.getPassword())) {
            throw new BadRequestException("password incorrectos");
        }

        return user;
    }

    @Override
    public User register(User user) {
        Pattern validEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmail.matcher(user.getEmail());
        if(!matcher.find()) {
            throw new BadRequestException("Email no valido");
        }

        Pattern validPassword = Pattern.compile("[A-Za-z\\d$@$!%*?&]{8,15}");
        matcher = validPassword.matcher(user.getPassword());
        if(!matcher.find()) {
            throw new BadRequestException("Password no valido");
        }

        User oldUser = userRepository.findByEmail(user.getEmail());
        if(oldUser != null){
            throw new BadRequestException("El correo ya existe");
        }
        try {
            user.setPassword(this.getPasswordEncoded(user.getPassword()));
            userRepository.save(user);
        } catch(Exception e) {
            throw new BadRequestException("datos incorrectos");
        }
        return user;
    }

    @Override
    public User getById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BadRequestException("No se ha encontrado el usuario"));
    }

    private String getPasswordEncoded(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private boolean isSameEncriptedPassword(String password, String encodedPassword) {
        return new BCryptPasswordEncoder() .matches(password, encodedPassword);
    }
}
