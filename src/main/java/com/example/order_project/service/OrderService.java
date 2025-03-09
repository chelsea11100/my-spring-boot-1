package com.example.order_project.service;

import com.example.order_project.entity.Order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderService {
    // 创建订单
    Order createOrder(Order order);

    // 根据用户ID查询订单
    List<Order> getOrdersByUserId(Long userId);

    // 根据工作人员ID查询订单
    List<Order> getOrdersByStaffId(Long staffId);

    // 更新订单状态
    Order updateOrderStatus(Long orderId, String status);

    // 统计订单
    List<Order> getOrdersByStatus(String status);

    //查询历史订单、查看订单详情、取消订单的请求
    List<Order> getOrderHistory(Long userId);
    Order getOrderDetails(Long orderId);
    boolean cancelOrder(Long orderId, Long userId,String role);

    // 订单申诉处理
    void handleOrderAppeal(Long orderId, String appealReason);

    // AI自动派单
    void autoAssignOrder(Order order);

    void submitOrderFeedback(Long orderId, String feedback, String result);
}