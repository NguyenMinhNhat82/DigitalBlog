package com.nmn.service.impl;

import com.nmn.dto.ChangePasswordDTO;
import com.nmn.dto.UserDTO;
import com.nmn.dto.mapper.UserMapper;
import com.nmn.dto.mapper.UserReponseMapper;
import com.nmn.dto.response.UserResponseDTO;
import com.nmn.model.Permission;
import com.nmn.model.Users;
import com.nmn.model.enumType.Role;
import com.nmn.repository.PermissionRepository;
import com.nmn.repository.UserRepository;
import com.nmn.repository.UserRepositoryCus;
import com.nmn.service.UserService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepositoryCus userRepositoryCus;

    @Autowired
    private UserReponseMapper userReponseMapper;

    @Autowired
    private PermissionRepository  permissionRepository;

    @Override
    public Users readProfile(Integer id) {
        return userRepository.findUsersById(id);
    }

    @Override
    public String changePassword(Integer id, ChangePasswordDTO changePasswordDTO) {
        try {
            if(!changePasswordDTO.getConfirmPassword().equals(changePasswordDTO.getNewPassword()))
                return "New Password and Confirm Password not match";
            Users user = userRepository.findUsersById(id);
            if(user == null)
                throw new UsernameNotFoundException("not found user");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(encoder.matches(changePasswordDTO.getCurrentPassword(),user.getPassword())) {
                user.setPassword(new BCryptPasswordEncoder().encode(changePasswordDTO.getNewPassword()));
                userRepository.save(user);
                return "SUCCESS";
            }
            return "Wrong password";
        }catch (Exception exception){
            return exception.getMessage();
        }
    }

    @Override
    public UserResponseDTO addOrUpdateProfile(UserDTO userDTO) {
        if(!EnumUtils.isValidEnum(Role.class,userDTO.getRole()) && (userDTO.getId() == 0 || userDTO.getId() == null))
            userDTO.setRole("CUSTOMER_USER");
        return userReponseMapper.userResponseDTO(userRepository.save(userMapper.toEntity(userDTO)));
    }
    @Override
    public Boolean deleteUser(Integer id) {
        try {
            userRepository.delete(userRepository.findUsersById(id));
            return  true;
        }
        catch (Exception ex){
            return false;
        }
    }

    @Override
    public List<UserResponseDTO> getListUser(Map<String, String> params) {
        return userReponseMapper.toListResponse(userRepositoryCus.getListUser(params));
    }

    @Override
    public Boolean activateUser(Integer id) {
        try {
            Users user = userRepository.findUsersById(id);
            user.setIsActivate(true);
            userRepository.save(user);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Boolean deActivateUser(Integer id) {
        try {
            Users user = userRepository.findUsersById(id);
            user.setIsActivate(false);
            userRepository.save(user);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public Users findUserByUserName(String userName) {
        return userRepository.findUsersByUsername(userName);
    }

    @Override
    public Users getUserbyId(Integer id) {
        return userRepository.findUsersById(id);
    }

    @Override
    public String givePermissionToSaveArticle(Integer id) {
        try{

            Users user = userRepository.findUsersById(id);
            Permission  permission = permissionRepository.getPermissionByUser(user);
            if(permission!= null)
                return "This user already has permission";
            Permission p = new Permission();
            p.setUser(user);
            permissionRepository.save(p);
            return  "SUCCESS";
        }catch (Exception ex){
            return ex.getMessage();
        }
    }

    @Override
    public String dennyPermissionToSaveArticle(Integer id) {
        try{
            Users user = userRepository.findUsersById(id);
            Permission permission  = permissionRepository.getPermissionByUser(user);
            if (permission == null)
                return "This user does not have permissions";
            permissionRepository.delete(permission);
            return  "SUCCESS";
        }catch (Exception ex){
            return ex.getMessage();
        }
    }


}
