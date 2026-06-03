package com.library.service.impl;

import com.library.entity.BookCategory;
import com.library.mapper.BookCategoryMapper;
import com.library.service.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {
    
    private final BookCategoryMapper bookCategoryMapper;
    
    @Override
    public List<BookCategory> findAll() {
        return bookCategoryMapper.findAll();
    }
    
    @Override
    public List<BookCategory> findAllWithBookCount() {
        return bookCategoryMapper.findAllWithBookCount();
    }
    
    @Override
    public BookCategory findById(Long id) {
        return bookCategoryMapper.findById(id);
    }
    
    @Override
    public boolean add(BookCategory category) {
        if (bookCategoryMapper.findByName(category.getName()) != null) {
            return false;
        }
        return bookCategoryMapper.insert(category) > 0;
    }
    
    @Override
    public boolean update(BookCategory category) {
        BookCategory existing = bookCategoryMapper.findByName(category.getName());
        if (existing != null && !existing.getId().equals(category.getId())) {
            return false;
        }
        return bookCategoryMapper.update(category) > 0;
    }
    
    @Override
    public boolean delete(Long id) {
        return bookCategoryMapper.delete(id) > 0;
    }
}
