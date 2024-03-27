package com.nmn.service.impl;

import com.nmn.dto.CommentDTO;
import com.nmn.dto.mapper.CommentMapper;
import com.nmn.model.Comments;
import com.nmn.repository.CommentRepository;
import com.nmn.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public Comments saveCommentToArticle(CommentDTO comment) throws Exception {
        if(comment.getUserID() ==0 ||comment.getUserID()== null)
            throw new Exception("User can not be null");
        return commentRepository.save(commentMapper.toEntity(comment));
    }
}
