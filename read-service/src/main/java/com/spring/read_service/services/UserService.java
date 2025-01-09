package com.spring.read_service.services;
import com.spring.read_service.entities.Users;
import com.spring.read_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private UserRepo userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    /**
     * Registers a new user by encoding their password and saving them to the repository.
     */
    public Users registerUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        // Default role as USER, can be customized further
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(Arrays.asList("USER"));
        }
        return userRepo.save(user);
    }
    /**
     * Verifies the user credentials and generates a JWT token.
     */
    public String verify(Users user) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (auth.isAuthenticated()) {
            Users foundUser = userRepo.findByUsername(user.getUsername());
            if (foundUser != null) {
                // Generate token including roles
                return jwtService.generateToken(foundUser.getUsername(), foundUser.getRoles());
            }
        }
        return "Authentication Failed";
    }
}






