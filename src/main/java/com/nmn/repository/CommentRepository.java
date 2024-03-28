package com.nmn.repository;

import com.nmn.model.Articles;
import com.nmn.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments,Integer> {
    List<Comments> findAllByArticleId(Articles articles);
}
