package com.spring.read_service.services;

import com.spring.read_service.entities.Users;
import com.spring.read_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager AuthManager;

    @Autowired
    UserRepo userRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users registerUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    public String verify(Users user) {
        Authentication auth=AuthManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(auth.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        else {
            return "Authentication Failed";
        }
    }
}
