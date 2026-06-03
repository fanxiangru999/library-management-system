package com.library.service.impl;

import com.library.entity.Admin;
import com.library.entity.Student;
import com.library.entity.User;
import com.library.mapper.AdminMapper;
import com.library.mapper.StudentMapper;
import com.library.mapper.UserMapper;
import com.library.service.UserService;
import com.library.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final StudentMapper studentMapper;
    
    @Override
    public Map<String, Object> login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (!PasswordUtil.matches(password, user.getPassword())) {
            return null;
        }
        if (user.getStatus() != 1) {
            return null;
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        
        if ("ADMIN".equals(user.getRole())) {
            Admin admin = adminMapper.findByUserId(user.getId());
            result.put("profile", admin);
        } else {
            Student student = studentMapper.findByUserId(user.getId());
            result.put("profile", student);
        }
        
        return result;
    }
    
    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return false;
        }
        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            return false;
        }
        return userMapper.updatePassword(userId, PasswordUtil.encode(newPassword)) > 0;
    }
    
    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }
}
