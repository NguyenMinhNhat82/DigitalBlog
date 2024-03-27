package com.nmn.service.impl;

import com.nmn.repository.RedisRepository;
import com.nmn.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisRepository tokenRepository;
    @Override
    public void saveToken(int idUser, String token) {
        tokenRepository.saveUserToken(idUser,token);
    }

    @Override
    public void deleteToken(String token) {
        tokenRepository.deleteToken(token);
    }

    @Override
    public Integer findTokenByUser(String token) {
        return tokenRepository.getTokenByIdUser(token);
    }
}
