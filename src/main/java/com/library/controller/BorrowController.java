package com.library.controller;

import com.library.entity.BorrowRecord;
import com.library.service.BorrowRecordService;
import com.library.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowController {
    
    private final BorrowRecordService borrowRecordService;
    
    @GetMapping("/list")
    public Result<List<BorrowRecord>> list(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(borrowRecordService.findAll());
    }
    
    @GetMapping("/current")
    public Result<List<BorrowRecord>> currentList(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(borrowRecordService.findAllCurrent());
    }
    
    @GetMapping("/overdue")
    public Result<List<BorrowRecord>> overdueList(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(borrowRecordService.findOverdue());
    }
    
    @GetMapping("/my")
    public Result<List<BorrowRecord>> myBorrowList(HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) {
            return Result.error(401, "未登录或非学生账号");
        }
        return Result.success(borrowRecordService.findByStudentId(studentId));
    }
    
    @GetMapping("/my/current")
    public Result<List<BorrowRecord>> myCurrentBorrowList(HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) {
            return Result.error(401, "未登录或非学生账号");
        }
        return Result.success(borrowRecordService.findCurrentByStudentId(studentId));
    }
    
    @PostMapping("/borrow")
    public Result<?> borrow(@RequestBody Map<String, Long> params, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) {
            return Result.error(401, "未登录或非学生账号");
        }
        
        Long bookId = params.get("bookId");
        if (bookId == null) {
            return Result.error("图书ID不能为空");
        }
        
        String error = borrowRecordService.borrow(studentId, bookId);
        if (error != null) {
            return Result.error(error);
        }
        return Result.success();
    }
    
    @PostMapping("/return/{id}")
    public Result<?> returnBook(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Result.error(401, "未登录");
        }
        
        String error = borrowRecordService.returnBook(id);
        if (error != null) {
            return Result.error(error);
        }
        return Result.success();
    }
}
