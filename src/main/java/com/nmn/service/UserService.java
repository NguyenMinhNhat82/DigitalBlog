package com.nmn.service;

import com.nmn.dto.UserDTO;
import com.nmn.model.Users;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    public Users readProfile(Integer id);

    public Boolean changePassword(Integer id, String password);

    public Users addOrUpdateProfile(UserDTO userDTO);
    public Boolean deleteUser(Integer id);

    public List<Users> getListUser(Map<String,String> params);

    public Boolean activateUser(Integer id);
    public Boolean deActivateUser(Integer id);

    public Users findUserByUserName(String userName);
    Users getUserbyId(Integer id);

    public String givePermissionToSaveArticle(Integer id);
    public String dennyPermissionToSaveArticle(Integer id);
}
