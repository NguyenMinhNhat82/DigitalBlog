package com.nmn.controller;

import com.nmn.dto.AuthenticationDTO;
import com.nmn.dto.ChangePasswordDTO;
import com.nmn.dto.UserDTO;
import com.nmn.dto.response.AuthenticationResponse;
import com.nmn.model.Users;
import com.nmn.service.AuthService;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    UserService  userService;


    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthService authService;


    //Update profile of own user
    @Operation(summary = "Update profile", description = "Update profile")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update-profile")
    ResponseEntity<Users> updateProfile(Principal user, @RequestBody UserDTO userDTO){
        Users userBeforeUpdate = userService.findUserByUserName(user.getName());
        userDTO.setId(userBeforeUpdate.getId());
        userDTO.setCanSaveArticle(userBeforeUpdate.getCanSaveArticle());
        return new ResponseEntity<>(userService.addOrUpdateProfile(userDTO), HttpStatus.OK);
    }

    @Operation(summary = "Change password", description = "Change password")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{id}/change-password")
    ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO request, @PathVariable("id") Integer id){
        String response = userService.changePassword(id,request);
        if (response.equals("SUCCESS"))
            return new ResponseEntity<>(userService.changePassword(id,request), HttpStatus.OK);
        return new ResponseEntity<>(userService.changePassword(id,request), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/read-profile")
    @Operation(summary = "Read profile", description = "Read profile")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Users> getUser(Principal userAuth){
        Users user = userService.findUserByUserName(userAuth.getName());
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        String jwt = authService.login(authenticationDTO,response);

        return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);

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
