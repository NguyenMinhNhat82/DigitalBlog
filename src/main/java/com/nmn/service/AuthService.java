package com.nmn.service;

import com.nmn.dto.AuthenticationDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;

public interface AuthService {
    public String login(AuthenticationDTO authenticationDTO, HttpServletResponse response) throws IOException;
    String refeshToken(HttpServletRequest request);
}
