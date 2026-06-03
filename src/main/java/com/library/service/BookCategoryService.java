package com.library.service;

import com.library.entity.BookCategory;
import java.util.List;

public interface BookCategoryService {
    List<BookCategory> findAll();
    List<BookCategory> findAllWithBookCount();
    BookCategory findById(Long id);
    boolean add(BookCategory category);
    boolean update(BookCategory category);
    boolean delete(Long id);
}
