package com.sportMates.Service.Impl;

import com.sportMates.Entities.ChangePassword;
import com.sportMates.Entities.User;
import com.sportMates.Exception.BadRequestException;
import com.sportMates.Repository.UserRepository;
import com.sportMates.Service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        User oldUserEmail = userRepository.findByEmail(user.getEmail());
        if(oldUserEmail != null){
            throw new BadRequestException("El correo ya existe");
        }
        User oldUserUsername = userRepository.findByUsername(user.getUsername());
        if(oldUserUsername != null){
            throw new BadRequestException("El nombre de usuario ya existe");
        }

        LocalDate now = LocalDate.now().withYear(LocalDateTime.now().getYear()-18);
        if(now.compareTo(user.getBornDate())<=0){
            throw new BadRequestException("Debes tener al menos 18 a??os para registrarte");
        }

        try {
            user.setPassword(this.getPasswordEncoded(user.getPassword()));
            userRepository.save(user);
        } catch(Exception e) {
            throw new BadRequestException("Datos incorrectos");
        }
        return user;
    }

    @Override
    public User getById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BadRequestException("No se ha encontrado el usuario"));
    }

    @Override
    public Boolean isUsernameInUse(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    @Override
    public Boolean isEmailInUse(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    @Override
    public Boolean updateAvatar(String avatar, int userId) {
        User user = this.getById(userId);
        user.setAvatar(avatar);
        userRepository.save(user);
        return true;

    }

    @Override
    public Boolean changePassword(ChangePassword changePassword, int userId) {
        User user = this.getById(userId);
        String currentPasswordEncripted = this.getPasswordEncoded(changePassword.getCurrent());
        if(!this.isSameEncriptedPassword(changePassword.getCurrent(), user.getPassword())){
            throw new BadRequestException("La contrase??a actual no coincide");
        }

        Pattern validPassword = Pattern.compile("[A-Za-z\\d$@$!%*?&]{8,15}");
        Matcher matcher = validPassword.matcher(changePassword.getNewP());
        if(!matcher.find()) {
            throw new BadRequestException("Nueva contrase??a no valida");
        }

        user.setPassword(this.getPasswordEncoded(changePassword.getNewP()));
        userRepository.save(user);

        return true;
    }

    private String getPasswordEncoded(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    private boolean isSameEncriptedPassword(String password, String encodedPassword) {
        return new BCryptPasswordEncoder() .matches(password, encodedPassword);
    }
}
