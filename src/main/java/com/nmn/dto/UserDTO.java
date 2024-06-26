package com.nmn.dto;

import com.nmn.model.enumType.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Integer id;

    private String username;
    private  String password;


    private String role;

    private Boolean isActivate;


    private  String email;
    private String phone;
    private String firstName;
    private String lastName;
    private Date dob;

    public UserDTO() {
    }
}
