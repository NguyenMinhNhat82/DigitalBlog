package com.nmn.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChangePasswordDTO {
    private String newPassword;
    private String currentPassword;
    private String confirmPassword;
}
