package com.spring.read_service.controllers;

import com.spring.read_service.entities.Users;
import com.spring.read_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
     return service.register(user.getUsername(),user.getPassword(),user.getRoles().stream().map(role->role.getRole()).collect(Collectors.toList()));
    }


    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        return service.verify(user);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String getUser() {
        return "users retrived";
    }

}
