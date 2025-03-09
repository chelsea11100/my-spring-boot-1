package com.example.order_project.controller;

import com.example.order_project.entity.User;
import com.example.order_project.security.UserPrincipal;
import com.example.order_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserPrincipal> loginUser(@RequestBody User user) {
        Optional<UserPrincipal> optionalUser = userService.loginUser(user.getUsername(), user.getPassword());
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());
    }
  @PostMapping("/logout")
  @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
  public ResponseEntity<Void> logoutUser(HttpServletRequest request, HttpServletResponse response) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication != null) {
          SecurityContextHolder.getContext().setAuthentication(null);
          HttpSession session = request.getSession(false);
          if (session != null) {
              session.invalidate();
          }
      }
      return ResponseEntity.noContent().build();
  }
  //修改用户信息
  @PutMapping("/profile")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<User> updateProfile(@RequestBody User user) {
      Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
      User updatedUser = userService.updateUserProfile(userId, user);
      return ResponseEntity.ok(updatedUser);
  }
  //展示用户信息
    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getProfile() {
        Long userId = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User userProfile = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfile);
    }

}