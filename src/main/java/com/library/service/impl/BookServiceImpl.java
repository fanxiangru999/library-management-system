package com.library.service.impl;

import com.library.entity.Book;
import com.library.mapper.BookMapper;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    
    private final BookMapper bookMapper;
    
    @Override
    public List<Book> findAll() {
        return bookMapper.findAll();
    }
    
    @Override
    public List<Book> search(String keyword) {
        return bookMapper.search(keyword);
    }
    
    @Override
    public List<Book> findByCategory(Long categoryId) {
        return bookMapper.findByCategory(categoryId);
    }
    
    @Override
    public Book findById(Long id) {
        return bookMapper.findById(id);
    }
    
    @Override
    public boolean add(Book book) {
        if (bookMapper.findByBookNo(book.getBookNo()) != null) {
            return false;
        }
        if (book.getAvailableCount() == null) {
            book.setAvailableCount(book.getTotalCount());
        }
        return bookMapper.insert(book) > 0;
    }
    
    @Override
    public boolean update(Book book) {
        Book existing = bookMapper.findByBookNo(book.getBookNo());
        if (existing != null && !existing.getId().equals(book.getId())) {
            return false;
        }
        return bookMapper.update(book) > 0;
    }
    
    @Override
    public boolean delete(Long id) {
        return bookMapper.delete(id) > 0;
    }
    
    @Override
    public int count() {
        return bookMapper.count();
    }
    
    @Override
    public Integer totalBooks() {
        Integer total = bookMapper.totalBooks();
        return total != null ? total : 0;
    }
    
    @Override
    public List<Book> findHotBooks(int limit) {
        return bookMapper.findHotBooks(limit);
    }
}
