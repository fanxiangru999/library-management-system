package com.library.service;

import com.library.entity.Book;
import java.util.List;

public interface BookService {
    List<Book> findAll();
    List<Book> search(String keyword);
    List<Book> findByCategory(Long categoryId);
    Book findById(Long id);
    boolean add(Book book);
    boolean update(Book book);
    boolean delete(Long id);
    int count();
    Integer totalBooks();
    List<Book> findHotBooks(int limit);
}
