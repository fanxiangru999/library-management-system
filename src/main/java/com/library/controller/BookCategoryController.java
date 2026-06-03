package com.library.controller;

import com.library.entity.BookCategory;
import com.library.service.BookCategoryService;
import com.library.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class BookCategoryController {
    
    private final BookCategoryService bookCategoryService;
    
    @GetMapping("/list")
    public Result<List<BookCategory>> list() {
        return Result.success(bookCategoryService.findAll());
    }
    
    @GetMapping("/listWithCount")
    public Result<List<BookCategory>> listWithCount() {
        return Result.success(bookCategoryService.findAllWithBookCount());
    }
    
    @GetMapping("/{id}")
    public Result<BookCategory> get(@PathVariable Long id) {
        BookCategory category = bookCategoryService.findById(id);
        if (category == null) {
            return Result.error("分类不存在");
        }
        return Result.success(category);
    }
    
    @PostMapping("/add")
    public Result<?> add(@RequestBody BookCategory category, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        boolean success = bookCategoryService.add(category);
        if (!success) {
            return Result.error("添加失败，分类名称已存在");
        }
        return Result.success();
    }
    
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody BookCategory category, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        category.setId(id);
        boolean success = bookCategoryService.update(category);
        if (!success) {
            return Result.error("更新失败，分类名称已存在");
        }
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        boolean success = bookCategoryService.delete(id);
        if (!success) {
            return Result.error("删除失败");
        }
        return Result.success();
    }
}
