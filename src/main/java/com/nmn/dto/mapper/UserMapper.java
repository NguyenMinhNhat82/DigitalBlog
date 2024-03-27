package com.nmn.dto.mapper;

import com.nmn.dto.UserDTO;
import com.nmn.model.Users;

import com.nmn.model.enumType.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(Users user){
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setRole(String.valueOf(user.getRole()));
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        userDto.setIsActivate(user.getIsActivate());
        return userDto;
    }

    public Users toEntity(UserDTO userDTO){
        Users user = new Users();
        if(userDTO.getId() == null){
            user.setId(0);
        }
        else
            user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setRole(Role.valueOf(userDTO.getRole()));
        user.setIsActivate(userDTO.getIsActivate());
        return user;
    }
}
