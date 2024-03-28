package com.nmn.service.impl;

import com.nmn.dto.ArticleDTO;
import com.nmn.dto.ArticleRequestDTO;
import com.nmn.dto.mapper.ArticleMapper;
import com.nmn.dto.mapper.ArticleRequestMapper;
import com.nmn.model.Articles;
import com.nmn.model.Users;
import com.nmn.model.enumType.Role;
import com.nmn.repository.ArticleRepository;
import com.nmn.repository.ArticleRepositoryCus;
import com.nmn.repository.PermissionRepository;
import com.nmn.service.ArticleService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Permission;
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

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ArticleRequestMapper articleRequestMapper;
    @Override
    public List<ArticleDTO> getListArticle(Map<String, String> params) {
        return  articleMapper.toListArticleDTO(articleRepositoryCus.getListArticles(params));
    }


    @Override
    public ArticleDTO saveArticle(ArticleRequestDTO articleReArticleDTO, Users users) {
        if((permissionRepository.getPermissionByUser(users) !=null && users.getRole().equals(Role.CUSTOMER_USER)
                || users.getRole().equals(Role.SYS_ADMIN))) {
            Articles articles = articleRequestMapper.toEntity(articleReArticleDTO);
            articles.setCreatedBy(users);
            return articleMapper.toDTO(articleRepository.save(articles));
        }
        throw new RuntimeException("User dont have permission to save");
    }


    @Override
    public void deleteArticles(Integer id) throws Exception {
            Articles articles = articleRepository.findArticlesById(id);
            if(articles == null)
                throw new Exception("Not found articles");
            articleRepository.delete(articles);

    }
}
