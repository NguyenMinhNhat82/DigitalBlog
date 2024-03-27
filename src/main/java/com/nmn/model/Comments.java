package com.nmn.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userId;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Articles articleId;

}
