package com.spring.read_service.services;

import com.spring.read_service.entities.Role;
import com.spring.read_service.entities.Users;
import com.spring.read_service.repository.RoleRepo;
import com.spring.read_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UserRepo repo;

    @Autowired
    private RoleRepo roleRepo;


    public Users register(String username, String password,List<String> roleName) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        List<Role> role=roleRepo.findByRoleIn(roleName);

        user.setRoles(role);

        return repo.save(user);


    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername(),user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()));
        } else {
            return "fail";
        }
    }
}
