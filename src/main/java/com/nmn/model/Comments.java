package com.nmn.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Table(name = "comments")
@Entity
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private  String content;
    private LocalDateTime createdDate;
    private  LocalDateTime updatedDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Articles articleId;

}
