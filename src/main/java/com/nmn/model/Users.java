package com.nmn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nmn.model.enumType.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private  String password;


    private Role role;

    private Boolean canSaveArticle;

    private Boolean isActivate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Articles> articles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Comments> comments = new HashSet<>();


}
