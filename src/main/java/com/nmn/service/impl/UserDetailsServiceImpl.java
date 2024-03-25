package com.nmn.service.impl;



import com.nmn.model.Users;
import com.nmn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Write Logic to get the user from the DB
        Users user = userRepository.findUsersByUsername(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found",null);
        }
        if (!user.getIsActivate())
            throw new RuntimeException("User is not activate");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
