package com.library.service;

import com.library.entity.User;
import java.util.Map;

public interface UserService {
    Map<String, Object> login(String username, String password);
    boolean changePassword(Long userId, String oldPassword, String newPassword);
    User findById(Long id);
}
