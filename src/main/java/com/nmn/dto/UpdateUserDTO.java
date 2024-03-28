package com.nmn.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateUserDTO {
    private String username;
    private  String email;
    private String phone;
    private String firstName;
    private String lastName;
    private Date dob;
}
