package com.nmn.service.impl;

import com.nmn.dto.CommentDTO;
import com.nmn.dto.mapper.CommentMapper;
import com.nmn.model.Comments;
import com.nmn.repository.CommentRepository;
import com.nmn.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public CommentDTO saveCommentToArticle(CommentDTO comment) throws Exception {
        if(comment.getUserID() ==0 ||comment.getUserID()== null)
            throw new Exception("User can not be null");
        return commentMapper.toDto(commentRepository.save(commentMapper.toEntity(comment)));
    }

    @Override
    public List<CommentDTO> getAllComment() {
        return commentMapper.toDTOList(commentRepository.findAll());
    }
}
