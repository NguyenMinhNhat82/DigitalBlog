package com.nmn.service;

import com.nmn.dto.ArticleDTO;
import com.nmn.model.Articles;
import com.nmn.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public interface ArticleService {
    public List<Articles> getListArticle(Map<String, String> params);

    public Articles saveArticle(ArticleDTO articleDTO, Users users);

    public void deleteArticles(Integer id);
}
