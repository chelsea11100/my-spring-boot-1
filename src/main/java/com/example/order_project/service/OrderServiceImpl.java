package com.example.order_project.service;

import com.example.order_project.entity.Order;
import com.example.order_project.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;


    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //用户创建订单
    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    //根据用户id查询订单
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }

    //根据工作人员id查询订单
    @Override
    public List<Order> getOrdersByStaffId(Long staffId) {
        return orderRepository.findByStaff_Id(staffId);
    }

    //更新订单状态
    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }

    //根据订单状态查找订单
    @Override
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    //查询历史订单、查看订单详情、取消订单的请求
    @Override
    public List<Order> getOrderHistory(Long userId) {
        return orderRepository.findByUser_IdAndStatusNotEquals(userId, "已取消");
    }

    @Override
    public Order getOrderDetails(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public boolean cancelOrder(Long orderId, Long userId, String role) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            if ("USER".equals(role)) {
                if ("待处理".equals(existingOrder.getStatus())) {
                    existingOrder.setStatus("已取消");
                    orderRepository.save(existingOrder);
                    return true;
                }
            } else if ("ADMIN".equals(role)) {
                //定义的是id相同就是订单相同（但好像不太合理，再说吧）
                boolean isDuplicateOrder = orderRepository.existsByIdAndNotEqualUserId(orderId, userId);
                if (isDuplicateOrder) {
                    existingOrder.setStatus("已取消");
                    orderRepository.save(existingOrder);
                    return true;
                }
            }
        }
        return false;
    }

    //用户评价订单
    @Override
    public void submitOrderFeedback(Long orderId, String feedback, String result) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setResult(result);
            existingOrder.setUserFeedback(feedback);
            orderRepository.save(existingOrder);
        }
    }

    @Override
    public void handleOrderAppeal(Long orderId, String appealReason) {
        // 记录订单申诉逻辑
    }

    @Override
    public void autoAssignOrder(Order order) {
        // AI自动派单逻辑
    }


}