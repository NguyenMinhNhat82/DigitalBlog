package com.nmn.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@Getter
@Setter
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private  String content;
    private LocalDateTime createdDate;
    private  LocalDateTime updatedDate;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created-by")
    private Users createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Comments> comments = new HashSet<>();

}
