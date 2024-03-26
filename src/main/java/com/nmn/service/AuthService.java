package com.nmn.service;

import com.nmn.dto.AuthenticationDTO;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthService {
    public String login(AuthenticationDTO authenticationDTO, HttpServletResponse response) throws IOException;
}
