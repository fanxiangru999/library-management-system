package com.library.controller;

import com.library.entity.Student;
import com.library.service.StudentService;
import com.library.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    
    private final StudentService studentService;
    
    @GetMapping("/list")
    public Result<List<Student>> list(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(studentService.findAll());
    }
    
    @GetMapping("/search")
    public Result<List<Student>> search(@RequestParam String keyword, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(studentService.search(keyword));
    }
    
    @GetMapping("/{id}")
    public Result<Student> get(@PathVariable Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        Student student = studentService.findById(id);
        if (student == null) {
            return Result.error("学生不存在");
        }
        return Result.success(student);
    }
    
    @GetMapping("/profile")
    public Result<Student> profile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        Student student = studentService.findByUserId(userId);
        if (student == null) {
            return Result.error("学生信息不存在");
        }
        return Result.success(student);
    }
    
    @PostMapping("/add")
    public Result<?> add(@RequestBody Map<String, Object> params, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        Student student = new Student();
        student.setStudentNo((String) params.get("studentNo"));
        student.setName((String) params.get("name"));
        student.setClassName((String) params.get("className"));
        student.setPhone((String) params.get("phone"));
        if (params.get("maxBorrow") != null) {
            student.setMaxBorrow((Integer) params.get("maxBorrow"));
        }
        
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        
        if (username == null || password == null) {
            return Result.error("用户名和密码不能为空");
        }
        
        boolean success = studentService.add(student, username, password);
        if (!success) {
            return Result.error("添加失败，学号或用户名已存在");
        }
        return Result.success();
    }
    
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Student student, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        student.setId(id);
        boolean success = studentService.update(student);
        if (!success) {
            return Result.error("更新失败");
        }
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        boolean success = studentService.delete(id);
        if (!success) {
            return Result.error("删除失败");
        }
        return Result.success();
    }
    
    @PutMapping("/{id}/status")
    public Result<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        Integer status = params.get("status");
        boolean success = studentService.updateStatus(id, status);
        if (!success) {
            return Result.error("操作失败");
        }
        return Result.success();
    }
    
    @PutMapping("/{id}/password")
    public Result<?> resetPassword(@PathVariable Long id, @RequestBody Map<String, String> params, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        String newPassword = params.get("password");
        if (newPassword == null || newPassword.isEmpty()) {
            return Result.error("密码不能为空");
        }
        
        boolean success = studentService.resetPassword(id, newPassword);
        if (!success) {
            return Result.error("重置密码失败");
        }
        return Result.success();
    }
}
