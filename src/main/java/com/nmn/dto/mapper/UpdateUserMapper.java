package com.nmn.dto.mapper;

import com.nmn.dto.UpdateUserDTO;
import com.nmn.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserMapper {

    public Users toEntity(UpdateUserDTO updateUserDTO){
        Users user  = new Users();
        user.setUsername(updateUserDTO.getUsername());

        user.setDob(updateUserDTO.getDob());
        user.setFirstName(updateUserDTO.getFirstName());
        user.setPhone(updateUserDTO.getPhone());
        user.setEmail(updateUserDTO.getEmail());
        user.setLastName(updateUserDTO.getLastName());
        return user;
    }
}
