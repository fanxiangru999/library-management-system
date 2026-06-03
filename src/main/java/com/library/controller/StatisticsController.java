package com.library.controller;

import com.library.service.BookService;
import com.library.service.BorrowRecordService;
import com.library.service.StudentService;
import com.library.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    
    private final BookService bookService;
    private final StudentService studentService;
    private final BorrowRecordService borrowRecordService;
    
    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("bookTypeCount", bookService.count());
        data.put("totalBooks", bookService.totalBooks());
        data.put("studentCount", studentService.count());
        data.put("borrowCount", borrowRecordService.count());
        data.put("currentBorrowCount", borrowRecordService.countCurrent());
        
        return Result.success(data);
    }
    
    @GetMapping("/monthly")
    public Result<List<Map<String, Object>>> monthlyStatistics(HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(borrowRecordService.monthlyStatistics());
    }
    
    @GetMapping("/topBorrowers")
    public Result<List<Map<String, Object>>> topBorrowers(@RequestParam(defaultValue = "10") int limit, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(borrowRecordService.topBorrowers(limit));
    }
    
    @GetMapping("/hotBooks")
    public Result<?> hotBooks(@RequestParam(defaultValue = "10") int limit, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        return Result.success(bookService.findHotBooks(limit));
    }
}
