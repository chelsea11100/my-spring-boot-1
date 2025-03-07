package com.example.order_project.controller;

import com.example.order_project.entity.Order;
import com.example.order_project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<Order>> getOrdersByStaffId(@PathVariable Long staffId) {
        List<Order> orders = orderService.getOrdersByStaffId(staffId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @PostMapping("/{orderId}/appeal")
    public ResponseEntity<Void> handleOrderAppeal(@PathVariable Long orderId, @RequestBody String appealReason) {
        orderService.handleOrderAppeal(orderId, appealReason);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/ai-assign")
    public ResponseEntity<Void> autoAssignOrder(@RequestBody Order order) {
        orderService.autoAssignOrder(order);
        return ResponseEntity.noContent().build();
    }
}