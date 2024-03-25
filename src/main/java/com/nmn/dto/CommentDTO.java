package com.nmn.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    private Integer id;
    private  String content;
    private LocalDateTime createdDate;
    private  LocalDateTime updatedDate;
    private Integer userID;
    private Integer articleID;
}
