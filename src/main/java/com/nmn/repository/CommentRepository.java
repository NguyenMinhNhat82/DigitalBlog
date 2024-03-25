package com.nmn.repository;

import com.nmn.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments,Integer> {
}
