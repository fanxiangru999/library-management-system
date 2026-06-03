package com.library.service;

import com.library.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> findAll();
    List<Student> search(String keyword);
    Student findById(Long id);
    Student findByUserId(Long userId);
    boolean add(Student student, String username, String password);
    boolean update(Student student);
    boolean delete(Long id);
    boolean updateStatus(Long id, Integer status);
    boolean resetPassword(Long id, String newPassword);
    int count();
}
