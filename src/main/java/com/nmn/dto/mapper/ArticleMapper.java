package com.nmn.dto.mapper;


import com.nmn.dto.ArticleDTO;
import com.nmn.model.Articles;
import com.nmn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    @Autowired
    UserMapper  userMapper;

    @Autowired
    UserRepository userRepository;
    public ArticleDTO toDTO(Articles articles){
        ArticleDTO  articleDTO = new ArticleDTO();
        articleDTO.setUserId(articles.getCreatedBy().getId());
        articleDTO.setId(articles.getId());
        articleDTO.setContent(articles.getContent());
        articleDTO.setTitle(articles.getTitle());
        articleDTO.setCreatedDate(articles.getCreatedDate());
        articleDTO.setUpdatedDate(articles.getUpdatedDate());
        return articleDTO;
    }

    public Articles toEntity(ArticleDTO  articleDTO){
        Articles articles = new Articles();
        if(articleDTO.getId() == null){
            articles.setId(0);
        }
        else articles.setId(articleDTO.getId());
        articles.setContent(articleDTO.getContent());
        articles.setTitle(articleDTO.getTitle());
        articles.setCreatedDate(articleDTO.getCreatedDate());
        articles.setUpdatedDate(articleDTO.getUpdatedDate());
        articles.setCreatedBy(userRepository.findUsersById(articleDTO.getUserId()));
        return articles;
    }
}
