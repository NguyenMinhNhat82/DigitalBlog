package com.nmn.service.impl;

import com.nmn.dto.ArticleDTO;
import com.nmn.dto.mapper.ArticleMapper;
import com.nmn.model.Articles;
import com.nmn.repository.ArticleRepository;
import com.nmn.repository.ArticleRepositoryCus;
import com.nmn.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public Articles saveArticle(ArticleDTO articleDTO) {
        return articleRepository.save(articleMapper.toEntity(articleDTO));
    }

    @Override
    public Boolean deleteArticles(Integer id) {
        try {
            Articles articles = articleRepository.findArticlesById(id);
            if(articles == null)
                return false;
            articleRepository.delete(articles);
            return true ;
        }
        catch (Exception ex){
            return false;
        }
    }
}
