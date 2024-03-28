package com.nmn.service.impl;

import com.nmn.dto.CommentDTO;
import com.nmn.dto.mapper.CommentMapper;
import com.nmn.model.Articles;
import com.nmn.model.Comments;
import com.nmn.repository.ArticleRepository;
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

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public CommentDTO saveCommentToArticle(CommentDTO comment) throws Exception {
        if(comment.getArticleID() ==0 ||comment.getArticleID()== null)
            throw new Exception("Article can not be null");
        if(articleRepository.findArticlesById(comment.getArticleID()) == null)
            throw new Exception("Article can not be found");
        return commentMapper.toDto(commentRepository.save(commentMapper.toEntity(comment)));
    }

    @Override
    public List<CommentDTO> getAllComment(Integer idArticle) throws Exception {
        Articles articles   = articleRepository.findArticlesById(idArticle);
        if (articles == null)
            throw new Exception("Article can not be found");
        return commentMapper.toDTOList(commentRepository.findAllByArticleId(articles));
    }
}
