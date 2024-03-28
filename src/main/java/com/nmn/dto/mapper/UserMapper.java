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

        userDto.setDob(user.getDob());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }

    public Users toEntity(UserDTO userDTO){
        Users user = new Users();
        if(userDTO.getId() == null){
            user.setId(0);
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        }
        else {
            user.setId(userDTO.getId());
        }
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setRole(Role.valueOf(userDTO.getRole()));
        user.setIsActivate(userDTO.getIsActivate());


        user.setDob(userDTO.getDob());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }
}
