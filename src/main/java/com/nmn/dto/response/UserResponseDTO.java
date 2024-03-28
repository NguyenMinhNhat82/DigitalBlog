package com.nmn.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponseDTO {
    private Integer id;
    private String username;
    private Boolean isActivate;
    private  String email;
    private String phone;
    private String firstName;
    private String lastName;
    private Date dob;
}
