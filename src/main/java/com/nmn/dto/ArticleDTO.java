package com.nmn.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDTO {
    private Integer id;

    private String title;
    private  String content;
    private LocalDateTime createdDate;
    private  LocalDateTime updatedDate;
    private Integer userId;
}
