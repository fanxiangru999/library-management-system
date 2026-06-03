package com.library.mapper;

import com.library.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {
    
    @Select("SELECT a.*, u.username, u.role, u.status FROM admin a LEFT JOIN user u ON a.user_id = u.id WHERE a.user_id = #{userId}")
    @Results({
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.role", column = "role"),
        @Result(property = "user.status", column = "status")
    })
    Admin findByUserId(Long userId);
    
    @Insert("INSERT INTO admin (user_id, name, phone) VALUES (#{userId}, #{name}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Admin admin);
}
