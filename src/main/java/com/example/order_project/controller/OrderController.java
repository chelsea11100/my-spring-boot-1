package com.example.order_project.controller;

import com.example.order_project.entity.Order;
import com.example.order_project.security.UserPrincipal;
import com.example.order_project.service.OrderService;
import com.example.order_project.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PerformanceService performanceRecordService;

    //用户创建订单
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    //用户填写订单反馈（涉及result和userfeedback）
    @PostMapping("/{orderId}/feedback")
    public ResponseEntity<Void> submitOrderFeedback(@PathVariable Long orderId,
                                                    @RequestBody String feedback,
                                                    @RequestBody String result) {
        orderService.submitOrderFeedback(orderId, feedback, result);
        return ResponseEntity.ok().build();
    }
    //管理人员查看所有订单时的搜索框 1.用户id 2.工作人员id
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getOrdersByStaffId(@PathVariable Long staffId) {
        List<Order> orders = orderService.getOrdersByStaffId(staffId);
        return ResponseEntity.ok(orders);
    }

    // 查询历史订单
    @GetMapping("/user/{userId}/history")
    @PreAuthorize("hasRole('USER') or hasRole('STAFF')")
    public ResponseEntity<List<Order>> getOrderHistory(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(orders);
    }

    // 查看订单详情
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetails(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 取消订单
    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> cancelOrder(@PathVariable Long orderId,
                                               @AuthenticationPrincipal UserPrincipal principal) {
        String role = principal.getAuthorities().iterator().next().getAuthority();
        boolean success = orderService.cancelOrder(orderId, principal.getId(),role);
        return ResponseEntity.ok(success);
    }

    //工作人员更新订单状态
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    //处理订单申诉（申诉逻辑还没写）
    @PostMapping("/{orderId}/appeal")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> handleOrderAppeal(@PathVariable Long orderId, @RequestBody String appealReason) {
        orderService.handleOrderAppeal(orderId, appealReason);
        return ResponseEntity.noContent().build();
    }

    //交给ai通过feedback设定工作量
    @PostMapping("/{orderId}/feedback")
    public ResponseEntity<Void> submitFeedback(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetails(orderId);
        if (order != null) {
            performanceRecordService.evaluateWorkload(order);
        }
        return ResponseEntity.noContent().build();
    }

    //ai自动派单（派单逻辑还没写）
    @PostMapping("/ai-assign")
    public ResponseEntity<Void> autoAssignOrder(@RequestBody Order order) {
        orderService.autoAssignOrder(order);
        return ResponseEntity.noContent().build();
    }
}