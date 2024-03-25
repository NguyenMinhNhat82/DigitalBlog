package com.nmn.repository;

import com.nmn.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findUsersById(Integer id);

    Users findUsersByUsername(String userName);
}
