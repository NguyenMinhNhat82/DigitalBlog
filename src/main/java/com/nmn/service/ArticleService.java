package com.nmn.service;

import com.nmn.dto.ArticleDTO;
import com.nmn.dto.ArticleRequestDTO;
import com.nmn.model.Articles;
import com.nmn.model.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public interface ArticleService {
    public List<ArticleDTO> getListArticle(Map<String, String> params);

    public ArticleDTO saveArticle(ArticleRequestDTO articleReArticleDTO, Users users);

    public void deleteArticles(Integer id);
}
