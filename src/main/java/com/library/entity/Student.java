package com.library.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Student {
    private Long id;
    private Long userId;
    private String studentNo;
    private String name;
    private String className;
    private String phone;
    private Integer maxBorrow;
    private LocalDateTime createTime;
    
    private User user;
    private Integer currentBorrowCount;
}
