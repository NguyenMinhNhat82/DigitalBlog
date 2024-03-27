package com.nmn.service.impl;

import com.nmn.dto.AuthenticationDTO;
import com.nmn.model.Users;
import com.nmn.repository.RedisRepository;
import com.nmn.repository.UserRepository;
import com.nmn.service.AuthService;
import com.nmn.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisRepository getRedisRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository  userRepository;
    @Autowired
    private JwtUtil jwtUtil;



    @Autowired
    private RedisRepository redisRepository;
    @Override
    public String login(AuthenticationDTO authenticationDTO, HttpServletResponse response) throws IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getUsername());
        Users user = userRepository.findUsersByUsername(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        if(!jwt.isEmpty()  || user != null){
            redisRepository.saveUserToken(user.getId(),jwt);
        }
        return jwt;
    }

    @Override
    public String refeshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return "";
        }
        String token = authHeader.substring(7);
        int idUser = redisRepository.getTokenByIdUser(token);
        if(idUser !=0){
            String newToken = jwtUtil.generateToken(userRepository.findUsersById(idUser).getUsername());

            redisRepository.saveUserToken(idUser,newToken);
            redisRepository.deleteToken(token);
            return newToken;
        }
        return "";
    }
}
