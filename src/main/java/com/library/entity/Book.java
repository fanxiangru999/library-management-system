package com.library.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Book {
    private Long id;
    private String bookNo;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private Long categoryId;
    private Integer totalCount;
    private Integer availableCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    private BookCategory category;
    
    private Integer borrowCount;
}
