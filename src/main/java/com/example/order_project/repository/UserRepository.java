package com.example.order_project.repository;

import com.example.order_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查找用户，用于登录验证
    Optional<User> findByUsername(String username);

    // 根据学号/工号查找用户
    Optional<User> findByStudentIdOrEmployeeId(String studentIdOrEmployeeId);

    // 根据角色查找用户（例如查询所有工作人员或管理员）
    List<User> findByRole(String role);
}