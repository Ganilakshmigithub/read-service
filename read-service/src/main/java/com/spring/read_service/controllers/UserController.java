package com.spring.read_service.controllers;


import com.spring.read_service.entities.Users;
import com.spring.read_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users user) {
        return userService.registerUser(user);
    }

    @DeleteMapping("/del/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "user deleted successfully";
    }

    @GetMapping("/all")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Users user) {
        return userService.verify(user);
    }
}