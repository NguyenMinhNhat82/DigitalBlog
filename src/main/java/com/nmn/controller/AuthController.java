package com.nmn.controller;

import com.nmn.dto.UserDTO;
import com.nmn.model.Users;
import com.nmn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    UserService  userService;


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
    ResponseEntity<String> changePassword(@RequestBody Map<String,String> request, @PathVariable("id") Integer id){
        String password = request.get("password");
        if(userService.changePassword(id,password))
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        return new ResponseEntity<>("FAIL", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/read-profile")
    @Operation(summary = "Read profile", description = "Read profile")
    @SecurityRequirement(name = "Bearer Authentication")
    ResponseEntity<Users> getUser(Principal userAuth){
        Users user = userService.findUserByUserName(userAuth.getName());
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }
}
