package com.example.order_project.service;

import com.example.order_project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // 用户注册
    User registerUser(User user);

    // 用户登录
    Optional<User> loginUser(String username, String password);

    // 根据用户ID查询用户信息
    Optional<User> getUserById(Long id);

    // 根据角色查询用户信息
    List<User> getUsersByRole(String role);

    // 更新用户信息
    User updateUser(Long id, User user);

    // 退出登录（清除用户会话信息）
    void logoutUser(Long userId);
    void deleteUser(Long userId);
}