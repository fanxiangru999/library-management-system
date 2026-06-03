package com.library.service.impl;

import com.library.entity.Student;
import com.library.entity.User;
import com.library.mapper.BorrowRecordMapper;
import com.library.mapper.StudentMapper;
import com.library.mapper.UserMapper;
import com.library.service.StudentService;
import com.library.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final BorrowRecordMapper borrowRecordMapper;
    
    @Override
    public List<Student> findAll() {
        return studentMapper.findAll();
    }
    
    @Override
    public List<Student> search(String keyword) {
        return studentMapper.search(keyword);
    }
    
    @Override
    public Student findById(Long id) {
        Student student = studentMapper.findById(id);
        if (student != null) {
            student.setCurrentBorrowCount(borrowRecordMapper.countCurrentByStudentId(id));
        }
        return student;
    }
    
    @Override
    public Student findByUserId(Long userId) {
        Student student = studentMapper.findByUserId(userId);
        if (student != null) {
            student.setCurrentBorrowCount(borrowRecordMapper.countCurrentByStudentId(student.getId()));
        }
        return student;
    }
    
    @Override
    @Transactional
    public boolean add(Student student, String username, String password) {
        if (studentMapper.findByStudentNo(student.getStudentNo()) != null) {
            return false;
        }
        if (userMapper.findByUsername(username) != null) {
            return false;
        }
        
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.encode(password));
        user.setRole("STUDENT");
        user.setStatus(1);
        userMapper.insert(user);
        
        student.setUserId(user.getId());
        if (student.getMaxBorrow() == null) {
            student.setMaxBorrow(5);
        }
        return studentMapper.insert(student) > 0;
    }
    
    @Override
    public boolean update(Student student) {
        return studentMapper.update(student) > 0;
    }
    
    @Override
    @Transactional
    public boolean delete(Long id) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            return false;
        }
        studentMapper.delete(id);
        return true;
    }
    
    @Override
    public boolean updateStatus(Long id, Integer status) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            return false;
        }
        return userMapper.updateStatus(student.getUserId(), status) > 0;
    }
    
    @Override
    public boolean resetPassword(Long id, String newPassword) {
        Student student = studentMapper.findById(id);
        if (student == null) {
            return false;
        }
        return userMapper.updatePassword(student.getUserId(), PasswordUtil.encode(newPassword)) > 0;
    }
    
    @Override
    public int count() {
        return studentMapper.count();
    }
}
