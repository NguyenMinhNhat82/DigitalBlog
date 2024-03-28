package com.nmn.dto.mapper;


import com.nmn.dto.ArticleRequestDTO;
import com.nmn.model.Articles;
import org.springframework.stereotype.Component;

@Component
public class ArticleRequestMapper {
    public Articles toEntity(ArticleRequestDTO articleRequestDTO){
        Articles articles = new Articles();
        articles.setTitle(articleRequestDTO.getTitle());
        articles.setContent(articleRequestDTO.getContent());
        articles.setUpdatedDate(articleRequestDTO.getUpdatedDate());
        articles.setCreatedDate(articleRequestDTO.getCreatedDate());
        return articles;
    }
}
