package com.nmn.controller;

import com.nmn.dto.AuthenticationDTO;
import com.nmn.dto.ChangePasswordDTO;
import com.nmn.dto.UpdateUserDTO;
import com.nmn.dto.UserDTO;
import com.nmn.dto.mapper.UpdateUserMapper;
import com.nmn.dto.mapper.UserMapper;
import com.nmn.dto.mapper.UserReponseMapper;
import com.nmn.dto.response.AuthenticationResponse;
import com.nmn.dto.response.UserResponseDTO;
import com.nmn.model.Users;
import com.nmn.service.AuthService;
import com.nmn.service.RedisService;
import com.nmn.service.UserService;
import com.nmn.service.impl.UserDetailsServiceImpl;
import com.nmn.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
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
    UpdateUserMapper updateUserMapper;

    @Autowired
    UserMapper  userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AuthService authService;
    @Autowired
    private UserReponseMapper userReponseMapper;


    //Update profile of own user
    @Operation(summary = "Update profile", description = "Update profile")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update-profile")
    ResponseEntity<?> updateProfile(Principal user, @RequestBody UpdateUserDTO userDTO){
        try {
            Users userBeforeUpdate = userService.findUserByUserName(user.getName());
            Users userUpdate = updateUserMapper.toEntity(userDTO);
            userUpdate.setId(userBeforeUpdate.getId());
            userUpdate.setPassword(userBeforeUpdate.getPassword());
            userUpdate.setRole(userBeforeUpdate.getRole());
            userUpdate.setIsActivate(userBeforeUpdate.getIsActivate());
            return new ResponseEntity<>(userService.addOrUpdateProfile(userMapper.toDTO(userUpdate)), HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
    ResponseEntity<?> getUser(Principal userAuth){
        try {
            Users user = userService.findUserByUserName(userAuth.getName());
            return new ResponseEntity<>(userReponseMapper.userResponseDTO(user), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationDTO authenticationDTO, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            String jwt = authService.login(authenticationDTO, response);
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "logout", description = "Log out")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
            if (token != null) {
                redisService.deleteToken(token);
                return new ResponseEntity<>(new AuthenticationResponse("Logged out"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new AuthenticationResponse("Log out fail"), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
        }
    }

    @Operation(summary = "refesh token", description = "Refresh JWT")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest httpServletRequest
    ) throws IOException {
        try {
            String newToken = authService.refeshToken(httpServletRequest);
            return new ResponseEntity<>(
                    new AuthenticationResponse(newToken),
                    HttpStatus.OK
            );
        }catch (Exception exception){
            return  new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
