package com.example.order_project.service;

import com.example.order_project.entity.Order;
import com.example.order_project.entity.PerformanceRecord;
import com.example.order_project.entity.User;
import com.example.order_project.security.UserPrincipal;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // 用户注册
    User registerUser(User user);

    // 用户登录
    Optional<UserPrincipal> loginUser(String username, String password);

    //管理员管理工作人员的四个方法（增删改查）
    User addStaff(User user);
    void deleteStaff(Long staffId);
    User updateStaffPermissions(Long staffId, User user);
    User getStaff(Long staffId);

    //管理员和工作人员查看所有未接订单及管理员手动派单
    List<Order> findUnassignedOrders();
    Order assignOrderToStaff(Long orderId, Long staffId);

    //管理员查看和修改自己的信息
    User getAdminProfile(Long adminId);
    User updateAdminProfile(Long adminId, User userDetails);

    //工作人员查看和修改自己的信息
    User getStaffProfile(Long staffId);
    User updateStaffProfile(Long staffId, User userDetails);
    //用户展示个人信息
    User getUserProfile(Long userId);

    //用户更新个人信息
    User updateUserProfile(Long userId, User user);


    void deleteUser(Long userId);
}