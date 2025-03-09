package com.example.order_project.controller;

import com.example.order_project.entity.Order;
import com.example.order_project.entity.User;
import com.example.order_project.security.UserPrincipal;
import com.example.order_project.service.OrderService;
import com.example.order_project.service.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    //登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<UserPrincipal> principal = userService.loginUser(user.getUsername(), user.getPassword());
        if (principal.isPresent()) {
            // 登录成功，返回UserPrincipal信息
            return ResponseEntity.ok(principal.get());
        } else {
            // 登录失败，返回错误信息
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
    //管理员管理工作人员的四个方法
    @PostMapping("/staff")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> addStaff(@RequestBody User user) {
        return ResponseEntity.ok(userService.addStaff(user));
    }

    @DeleteMapping("/staff/{staffId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long staffId) {
        userService.deleteStaff(staffId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/staff/{staffId}/permissions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateStaffPermissions(@PathVariable Long staffId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateStaffPermissions(staffId, user));
    }

    @GetMapping("/staff/{staffId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getStaff(@PathVariable Long staffId) {
        User staff = userService.getStaff(staffId);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //管理员和工作人员查看所有未接订单
    @GetMapping("/orders/unassigned")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Order>> listUnassignedOrders() {
        List<Order> orders = userService.findUnassignedOrders();
        return ResponseEntity.ok(orders);
    }

    // 手动派单给工作人员
    @PostMapping("/orders/{orderId}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> assignOrderToStaff(@PathVariable Long orderId, @RequestBody Long staffId) {
        Order updatedOrder = userService.assignOrderToStaff(orderId, staffId);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 展示管理员个人信息
    @GetMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getAdminProfile() {
        Long adminId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User adminProfile = userService.getAdminProfile(adminId);
        if (adminProfile != null) {
            return ResponseEntity.ok(adminProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 管理员修改个人信息
    @PutMapping("/profile")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateAdminProfile(@RequestBody User user) {
        Long adminId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User updatedAdmin = userService.updateAdminProfile(adminId, user);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 工作人员查看个人信息
    @GetMapping("/staff/profile")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<User> getStaffProfile() {
        Long staffId =((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User staffProfile = userService.getStaffProfile(staffId);
        if (staffProfile != null) {
            return ResponseEntity.ok(staffProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 工作人员修改个人信息
    @PutMapping("/staff/profile")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<User> updateStaffProfile(@RequestBody User user) {
        Long staffId =((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User updatedStaff = userService.updateStaffProfile(staffId, user);
        if (updatedStaff != null) {
            return ResponseEntity.ok(updatedStaff);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //登出
    @PostMapping("/logout")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        // 清除会话HttpSession
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.noContent().build();
    }



}