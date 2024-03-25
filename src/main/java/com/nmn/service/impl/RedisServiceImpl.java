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
    public void deleteToken(int idUser) {
        tokenRepository.deleteToken(idUser);
    }

    @Override
    public String findTokenByUser(int user) {
        return tokenRepository.getTokenByIdUser(user);
    }
}
