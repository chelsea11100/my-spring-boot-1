package com.example.order_project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")//数据库表orders以及下面所用到的user表
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 订单ID（主键）

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 用户ID（外键）

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = true)
    private User staff; // 工作人员ID（外键，可为空）

    @Column(nullable = false)
    private String status; // 订单状态

    @Column(nullable = false)
    private String deviceType; // 设备类型

    @Column(nullable = false)
    private String problemDescription; // 问题描述

    @Column(nullable = false)
    private LocalDateTime appointmentTime; // 预约时间

    @Column(nullable = false)
    private LocalDateTime createdAt; // 创建时间

    @Column(nullable = true)
    private LocalDateTime acceptedAt; // 接单时间

    @Column(nullable = true)
    private LocalDateTime completedAt; // 完成时间

    @Column(nullable = true)
    private String result; // 维修结果

    @Column(nullable = true)
    private String userFeedback; // 用户评价

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getStaff() {
        return staff;
    }

    public void setStaff(User staff) {
        this.staff = staff;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(LocalDateTime acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUserFeedback() {
        return userFeedback;
    }

    public void setUserFeedback(String userFeedback) {
        this.userFeedback = userFeedback;
    }
    public Long getStaffId() {
        if (this.staff != null) {
            return this.staff.getId();
        }
        return null;
    }

    public void setStaffId(Long staffId) {
        if (staffId != null) {
            User staff = new User();
            staff.setId(staffId);
            this.staff = staff;
        } else {
            this.staff = null;
        }
    }
}
