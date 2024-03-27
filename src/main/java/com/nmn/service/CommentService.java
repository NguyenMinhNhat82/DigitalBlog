package com.nmn.service;

import com.nmn.dto.CommentDTO;
import com.nmn.model.Comments;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;
import java.util.List;

@Service
public interface CommentService {
    public CommentDTO saveCommentToArticle(CommentDTO comment) throws Exception;
    public List<CommentDTO> getAllComment();
}
