package com.example.order_project.repository;

import com.example.order_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 根据用户ID查询订单，用于用户查看自己的订单
    List<Order> findByUser_Id(Long userId);

    // 根据工作人员ID查询订单，用于工作人员查看自己的接单记录
    List<Order> findByStaff_Id(Long staffId);

    // 根据订单状态查询订单，用于管理员查看特定状态的订单
    List<Order> findByStatus(String status);
}