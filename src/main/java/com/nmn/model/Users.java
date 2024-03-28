package com.nmn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nmn.model.enumType.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private  String password;


    private Role role;


    private Boolean isActivate;
    private  String email;
    private String phone;
    private String firstName;
    private String lastName;
    private Date dob;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Articles> articles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Comments> comments = new HashSet<>();
    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Permission permission;

    public Users(Integer id, String username, String password, Role role, Boolean isActivate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActivate = isActivate;
    }


    public Users() {
    }
}
