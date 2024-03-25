package com.nmn.service;

import com.nmn.dto.CommentDTO;
import com.nmn.model.Comments;
import org.springframework.stereotype.Service;

import javax.xml.stream.events.Comment;

@Service
public interface CommentService {
    public Comments saveCommentToArticle(CommentDTO comment);
}
