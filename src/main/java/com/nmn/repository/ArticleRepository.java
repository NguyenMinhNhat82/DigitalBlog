package com.nmn.repository;

import com.nmn.model.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Articles,Integer> {
    Articles findArticlesById(Integer id);
}
