package com.spring.read_service.services;

import com.spring.read_service.dtos.UserPrincipal;
import com.spring.read_service.dtos.Users;
import com.spring.read_service.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    UserPrincipal userPrincipal;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user=userRepo.findByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("User not found");
        return new UserPrincipal(user);
    }
}
