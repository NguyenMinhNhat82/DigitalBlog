package com.nmn.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
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

    @ManyToOne
    @JoinColumn(name = "created-by")
    @NotNull
    private Users createdBy;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articleId",fetch = FetchType.LAZY)
    private Set<Comments> comments = new HashSet<>();

}
