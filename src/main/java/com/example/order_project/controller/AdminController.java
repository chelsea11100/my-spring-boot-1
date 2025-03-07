package com.example.order_project.controller;

import com.example.order_project.entity.Order;
import com.example.order_project.entity.User;
import com.example.order_project.service.OrderService;
import com.example.order_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/manual-assign/{orderId}")
    public ResponseEntity<Order> manuallyAssignOrder(@PathVariable Long orderId, @RequestBody Long staffId) {
        Order order = orderService.updateOrderStatus(orderId, "ASSIGNED");
        order.setStaffId(staffId);
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, "ASSIGNED"));
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Additional endpoints for managing staff and data statistics can be added here
}