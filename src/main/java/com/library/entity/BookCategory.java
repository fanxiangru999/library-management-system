package com.library.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookCategory {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createTime;
    
    private Integer bookCount;
}
