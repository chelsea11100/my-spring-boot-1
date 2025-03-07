package com.example.order_project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")//数据库的users表（还未建立）
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 用户ID（主键）

    @Column(nullable = false, unique = true)
    private String username; // 账号

    @Column(nullable = false)
    private String password; // 密码（加密）

    @Column(nullable = false)
    private String studentIdOrEmployeeId; // 学号/工号

    @Column(nullable = false)
    private String role; // 角色（普通用户、工作人员、系统管理员）

    @Column(nullable = false)
    private LocalDateTime createdAt; // 创建时间


    @Column(nullable = true)
    private String specialty; // 擅长领域（工作人员）

    @Column(nullable = true)
    private String contactInfo; // 联系方式

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentIdOrEmployeeId() {
        return studentIdOrEmployeeId;
    }

    public void setStudentIdOrEmployeeId(String studentIdOrEmployeeId) {
        this.studentIdOrEmployeeId = studentIdOrEmployeeId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}