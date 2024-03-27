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
    @Bean
    public void setUp(){
        UserDTO users = new UserDTO(0,"nhat","123", "SYS_ADMIN",true,true);
        userService.addOrUpdateProfile(users);
        for(int i = 2; i< 10;i++){
            UserDTO userCus = new UserDTO(0,"nhat"+i,"123", "CUSTOMER_USER",true,true);
            userService.addOrUpdateProfile(userCus);
        }
        for(int i = 2; i< 10;i++){
            ArticleDTO articleDTO = new ArticleDTO(0,"title"+i,"content"+i, LocalDateTime.now(), LocalDateTime.now(),i);
            Users users1 = userService.getUserbyId(articleDTO.getUserId());
            articleService.saveArticle(articleDTO,users1);
        }
    }
}
