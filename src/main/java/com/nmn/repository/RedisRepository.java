package com.nmn.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RedisRepository {
    public static final String HASH_KEY = "JWT_TOKEN";

    @Autowired
    RedisTemplate template;
    public void saveUserToken(int idUser, String token){
        template.opsForHash().put(HASH_KEY,token,idUser);
    }

    public void deleteToken(String token){
        template.opsForHash().delete(HASH_KEY,token);
    }

    public  Integer getTokenByIdUser(String token){
       return  Integer.parseInt(template.opsForHash().get(HASH_KEY,token).toString());
    }

}
