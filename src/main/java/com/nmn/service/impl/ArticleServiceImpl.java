package com.nmn.service.impl;

import com.nmn.dto.ArticleDTO;
import com.nmn.dto.mapper.ArticleMapper;
import com.nmn.model.Articles;
import com.nmn.model.Users;
import com.nmn.model.enumType.Role;
import com.nmn.repository.ArticleRepository;
import com.nmn.repository.ArticleRepositoryCus;
import com.nmn.service.ArticleService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleRepositoryCus articleRepositoryCus;
    @Override
    public List<Articles> getListArticle(Map<String, String> params) {
        return  articleRepositoryCus.getListArticles(params);
    }

    @Override
    public Articles saveArticle(ArticleDTO articleDTO, Users user) {
        if((user.getCanSaveArticle() && user.getRole().equals(Role.CUSTOMER_USER)
                || user.getRole().equals(Role.SYS_ADMIN))) {
            return articleRepository.save(articleMapper.toEntity(articleDTO));
        }
        throw new RuntimeException("User dont have permission to save");
    }

    @Override
    public void deleteArticles(Integer id) {
        try {
            Articles articles = articleRepository.findArticlesById(id);
            if(articles == null)
                throw new Exception("Not found articles");
            articleRepository.delete(articles);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
