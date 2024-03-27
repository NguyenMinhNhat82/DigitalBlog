package com.nmn.repository;

import com.nmn.model.Permission;
import com.nmn.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {
    Permission getPermissionByUser(Users users);
}
