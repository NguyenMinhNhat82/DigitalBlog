package com.nmn;


import com.nmn.dto.ArticleDTO;
import com.nmn.dto.UserDTO;
import com.nmn.model.Articles;
import com.nmn.model.Users;
import com.nmn.model.enumType.Role;
import com.nmn.service.ArticleService;
import com.nmn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@SpringBootApplication
public class DigitalBlogApplication {
    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DigitalBlogApplication.class,args);
    }

}
