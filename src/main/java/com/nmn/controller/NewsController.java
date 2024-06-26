package com.nmn.controller;

import com.nmn.dto.ArticleDTO;
import com.nmn.dto.ArticleRequestDTO;
import com.nmn.dto.CommentDTO;
import com.nmn.model.Articles;
import com.nmn.model.Comments;
import com.nmn.model.Users;
import com.nmn.model.enumType.Role;
import com.nmn.service.ArticleService;
import com.nmn.service.CommentService;
import com.nmn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/article")
    ResponseEntity<List<ArticleDTO>> getListArticles(@RequestParam(required = false,defaultValue = "1") String page,
                                                     @RequestParam(required = false,defaultValue = "") String title) {
        Map<String, String> params = new HashMap<>();
        params.put("page",page);
        params.put("title",title);
        return new ResponseEntity<>(articleService.getListArticle(params), HttpStatus.OK);
    }

    @Operation(summary = "Save article", description = "Save article")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/article/save")
    ResponseEntity<?> saveArticle(@RequestBody ArticleRequestDTO articleDTO, Principal userAuth) {
        try {
            Users user = userService.findUserByUserName(userAuth.getName());
            return new ResponseEntity<>(articleService.saveArticle(articleDTO, user), HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Delete article", description = "Delete article")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/article/{id}/delete")
    ResponseEntity<String> deleteArticle(@PathVariable("id") Integer idArticle) {
        try {
            articleService.deleteArticles(idArticle);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/comment/save")
    ResponseEntity<?> saveComment(@RequestBody CommentDTO commentDTO, Principal user) throws Exception {
        try {
            if (user != null) {
                Users checkUser = userService.findUserByUserName(user.getName());
                commentDTO.setUserID(checkUser.getId());
            }
            return new ResponseEntity<>(commentService.saveCommentToArticle(commentDTO), HttpStatus.OK);
        }
        catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/article/{id}/comments")
    ResponseEntity<?> getComment(@PathVariable("id") Integer idArticle) throws Exception {
        try {
            return new ResponseEntity<>(commentService.getAllComment(idArticle),HttpStatus.OK );
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR );
        }

    }
}
