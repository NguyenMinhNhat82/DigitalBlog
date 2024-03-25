package com.nmn.service;

import com.nmn.dto.ArticleDTO;
import com.nmn.model.Articles;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public interface ArticleService {
    public List<Articles> getListArticle(Map<String, String> params);

    public Articles saveArticle(ArticleDTO articleDTO);

    public Boolean deleteArticles(Integer id);
}
