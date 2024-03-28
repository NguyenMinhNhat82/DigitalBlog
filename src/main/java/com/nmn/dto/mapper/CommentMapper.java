package com.nmn.dto.mapper;

import com.nmn.dto.CommentDTO;
import com.nmn.model.Comments;
import com.nmn.model.Users;
import com.nmn.repository.ArticleRepository;
import com.nmn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CommentMapper {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    public CommentDTO toDto(Comments comments){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comments.getId());
        commentDTO.setContent(comments.getContent());
        commentDTO.setCreatedDate(comments.getCreatedDate());
        commentDTO.setUpdatedDate(comments.getUpdatedDate());
        commentDTO.setUserID(comments.getUserId()==null?0:comments.getUserId().getId());
        commentDTO.setArticleID(comments.getArticleId().getId());
        return commentDTO;
    }

    public Comments toEntity(CommentDTO commentDTO){
        Comments comment  = new Comments();
        if(commentDTO.getArticleID() == null)
            comment.setId(0);
        else
            comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setUpdatedDate(commentDTO.getUpdatedDate());
        comment.setCreatedDate(commentDTO.getCreatedDate());
        comment.setUserId(userRepository.findUsersById(commentDTO.getUserID()));
        comment.setArticleId(articleRepository.findArticlesById(commentDTO.getArticleID()));
        return comment;

    }
    public List<CommentDTO> toDTOList(List<Comments> list){
        List<CommentDTO > list1 = new ArrayList<>();
        for(Comments c : list)
            list1.add(toDto(c));
        return list1;
    }
}
