package com.nmn.dto.mapper;


import com.nmn.dto.response.UserResponseDTO;
import com.nmn.model.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserReponseMapper {
    public UserResponseDTO userResponseDTO(Users users){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(users.getId());
        userResponseDTO.setUsername(users.getUsername());
        userResponseDTO.setIsActivate(users.getIsActivate());

        userResponseDTO.setDob(users.getDob());
        userResponseDTO.setEmail(users.getEmail());
        userResponseDTO.setPhone(users.getPhone());
        userResponseDTO.setFirstName(users.getFirstName());
        userResponseDTO.setLastName(users.getLastName());

        return userResponseDTO;
    }

    public List<UserResponseDTO> toListResponse(List<Users> list){
        List<UserResponseDTO> list1 = new ArrayList<>();
        for(Users u: list){
            list1.add(userResponseDTO(u));
        }
        return  list1;
    }
}
