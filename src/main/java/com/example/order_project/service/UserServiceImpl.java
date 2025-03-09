package com.example.order_project.service;

import com.example.order_project.entity.Order;
import com.example.order_project.entity.User;
import com.example.order_project.repository.OrderRepository;
import com.example.order_project.repository.UserRepository;
import com.example.order_project.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OrderRepository orderRepository;
    //用户注册
    @Override
    public User registerUser(User user) {
        if (user.getContactInfo() == null) {
            user.setContactInfo("未提供联系电话");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
    //用户登录
    @Override
    public Optional<UserPrincipal> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return Optional.of(UserPrincipal.create(user.get()));
        }
        return Optional.empty();
    }
    //管理员管理工作人员
    @Override
    public User addStaff(User user) {
        user.setRole("STAFF");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteStaff(Long staffId) {
        userRepository.deleteById(staffId);
    }

    @Override
    public User updateStaffPermissions(Long staffId, User user) {
        Optional<User> existingUser = userRepository.findById(staffId);
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();
            currentUser.setRole(user.getRole());
            return userRepository.save(currentUser);
        }
        return null;
    }

    @Override
    public User getStaff(Long staffId) {
        return userRepository.findById(staffId).orElse(null);
    }

    //管理员和工作人员查看所有未接订单及管理员手动派单

    @Override
    public List<Order> findUnassignedOrders() {
        return orderRepository.findByStatus("received");
    }

    @Override
    public Order assignOrderToStaff(Long orderId, Long staffId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order o = order.get();
            o.setStaffId(staffId);
            return orderRepository.save(o);
        }
        return null;
    }

    //管理员查看和修改自己的信息
    @Override
    public User getAdminProfile(Long adminId) {
        return userRepository.findById(adminId).orElse(null);
    }

    @Override
    public User updateAdminProfile(Long adminId, User userDetails) {
        Optional<User> existingUser = userRepository.findById(adminId);
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();
            currentUser.setUsername(userDetails.getUsername());
            currentUser.setContactInfo(userDetails.getContactInfo());
            return userRepository.save(currentUser);
        }
        return null;
    }
    //工作人员查看和修改自己的信息
    @Override
    public User getStaffProfile(Long staffId) {
        return userRepository.findById(staffId).orElse(null);
    }

    @Override
    public User updateStaffProfile(Long staffId, User userDetails) {
        Optional<User> existingUser = userRepository.findById(staffId);
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();
            currentUser.setUsername(userDetails.getUsername());
            currentUser.setSpecialty(userDetails.getSpecialty());
            currentUser.setContactInfo(userDetails.getContactInfo());
            return userRepository.save(currentUser);
        }
        return null;
    }

    //更新用户信息
    @Override
    public User updateUserProfile(Long userId, User newUserDetails) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();
            if (newUserDetails.getUsername() != null) {
                currentUser.setUsername(newUserDetails.getUsername());
            }
            if (newUserDetails.getContactInfo() != null) {
                currentUser.setContactInfo(newUserDetails.getContactInfo());
            }
            if (newUserDetails.getSpecialty() != null) {
                currentUser.setSpecialty(newUserDetails.getSpecialty());
            }
            return userRepository.save(currentUser);
        }
        return null;
    }
    @Override
    public User getUserProfile(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }


    //删除用户（管理员调用）
    @Override
    public void deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
        }
    }

}