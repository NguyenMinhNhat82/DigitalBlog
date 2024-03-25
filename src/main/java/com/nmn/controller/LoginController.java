package com.nmn.controller;


import com.nmn.dto.AuthenticationDTO;
import com.nmn.dto.response.AuthenticationResponse;
import com.nmn.model.Users;
import com.nmn.service.RedisService;
import com.nmn.service.UserService;
import com.nmn.service.impl.UserDetailsServiceImpl;
import com.nmn.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserService   userService;

    @Autowired
    private RedisService  redisService;

    @GetMapping("test")
    public String test()
    {
        return "test";
    }
    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(), authenticationDTO.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not activated");
            return null;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getUsername());
        Users user = userService.findUserByUserName(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        if(!jwt.isEmpty()  || user != null){
            redisService.saveToken(user.getId(),jwt);
        }
        return new AuthenticationResponse(jwt);

    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = "/current-user",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Users> currentUser(Principal user){
        Users u = userService.findUserByUserName(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }



    @Operation(summary = "logout", description = "Log out")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/logout")
    public AuthenticationResponse logout(Principal user){
        Users userAuthentication = userService.findUserByUserName(user.getName());
        if(user!= null){
            redisService.deleteToken(userAuthentication.getId());
            return new AuthenticationResponse("Logged out");
        }
        return new AuthenticationResponse("Log out fail");
    }
}
