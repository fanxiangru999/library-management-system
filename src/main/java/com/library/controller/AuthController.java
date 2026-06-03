package com.library.controller;

import com.library.entity.Student;
import com.library.entity.User;
import com.library.service.UserService;
import com.library.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserService userService;
    
    @PostMapping("/login")
    public Result<?> login(@RequestBody Map<String, String> params, HttpSession session) {
        String username = params.get("username");
        String password = params.get("password");
        
        if (username == null || password == null) {
            return Result.error("用户名和密码不能为空");
        }
        
        Map<String, Object> result = userService.login(username, password);
        if (result == null) {
            return Result.error("用户名或密码错误，或账号已被禁用");
        }
        
        User user = (User) result.get("user");
        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole());
        session.setAttribute("username", user.getUsername());
        
        if ("STUDENT".equals(user.getRole())) {
            Student student = (Student) result.get("profile");
            session.setAttribute("studentId", student.getId());
        }
        
        return Result.success(result);
    }
    
    @PostMapping("/logout")
    public Result<?> logout(HttpSession session) {
        session.invalidate();
        return Result.success();
    }
    
    @GetMapping("/info")
    public Result<?> info(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        
        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");
        
        Map<String, Object> info = Map.of(
            "userId", userId,
            "role", role,
            "username", username
        );
        
        return Result.success(info);
    }
    
    @PostMapping("/password")
    public Result<?> changePassword(@RequestBody Map<String, String> params, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        
        if (oldPassword == null || newPassword == null) {
            return Result.error("参数错误");
        }
        
        boolean success = userService.changePassword(userId, oldPassword, newPassword);
        if (!success) {
            return Result.error("原密码错误");
        }
        return Result.success();
    }
}
