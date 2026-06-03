package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import com.library.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    
    private final BookService bookService;
    
    @GetMapping("/list")
    public Result<List<Book>> list() {
        return Result.success(bookService.findAll());
    }
    
    @GetMapping("/search")
    public Result<List<Book>> search(@RequestParam String keyword) {
        return Result.success(bookService.search(keyword));
    }
    
    @GetMapping("/category/{categoryId}")
    public Result<List<Book>> findByCategory(@PathVariable Long categoryId) {
        return Result.success(bookService.findByCategory(categoryId));
    }
    
    @GetMapping("/{id}")
    public Result<Book> get(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book == null) {
            return Result.error("图书不存在");
        }
        return Result.success(book);
    }
    
    @GetMapping("/hot")
    public Result<List<Book>> hotBooks(@RequestParam(defaultValue = "10") int limit) {
        return Result.success(bookService.findHotBooks(limit));
    }
    
    @PostMapping("/add")
    public Result<?> add(@RequestBody Book book, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        boolean success = bookService.add(book);
        if (!success) {
            return Result.error("添加失败，图书编号已存在");
        }
        return Result.success();
    }
    
    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Book book, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        book.setId(id);
        boolean success = bookService.update(book);
        if (!success) {
            return Result.error("更新失败，图书编号已存在");
        }
        return Result.success();
    }
    
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return Result.error(403, "无权限");
        }
        
        boolean success = bookService.delete(id);
        if (!success) {
            return Result.error("删除失败");
        }
        return Result.success();
    }
}
