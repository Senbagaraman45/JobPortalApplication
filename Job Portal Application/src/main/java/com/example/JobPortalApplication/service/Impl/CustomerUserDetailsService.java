package com.example.JobPortalApplication.service.Impl;

import com.example.JobPortalApplication.entity.Users;
import com.example.JobPortalApplication.repository.UsersRepository;
import com.example.JobPortalApplication.utility.CustomUserDeatils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Autowired
    public CustomerUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=usersRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Could not found user"));
        return new CustomUserDeatils(users);
    }
}
