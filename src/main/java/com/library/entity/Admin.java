package com.library.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Admin {
    private Long id;
    private Long userId;
    private String name;
    private String phone;
    private LocalDateTime createTime;
    
    private User user;
}
