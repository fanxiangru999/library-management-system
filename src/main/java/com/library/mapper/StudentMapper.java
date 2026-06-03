package com.library.mapper;

import com.library.entity.Student;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StudentMapper {
    
    @Select("SELECT s.*, u.username, u.status as user_status FROM student s LEFT JOIN user u ON s.user_id = u.id WHERE s.user_id = #{userId}")
    @Results({
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.status", column = "user_status")
    })
    Student findByUserId(Long userId);
    
    @Select("SELECT s.*, u.username, u.status as user_status FROM student s LEFT JOIN user u ON s.user_id = u.id WHERE s.id = #{id}")
    @Results({
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.status", column = "user_status")
    })
    Student findById(Long id);
    
    @Select("SELECT s.*, u.username, u.status as user_status FROM student s LEFT JOIN user u ON s.user_id = u.id ORDER BY s.create_time DESC")
    @Results({
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.status", column = "user_status")
    })
    List<Student> findAll();
    
    @Select("SELECT s.*, u.username, u.status as user_status FROM student s LEFT JOIN user u ON s.user_id = u.id WHERE s.student_no LIKE CONCAT('%', #{keyword}, '%') OR s.name LIKE CONCAT('%', #{keyword}, '%') ORDER BY s.create_time DESC")
    @Results({
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.status", column = "user_status")
    })
    List<Student> search(String keyword);
    
    @Select("SELECT * FROM student WHERE student_no = #{studentNo}")
    Student findByStudentNo(String studentNo);
    
    @Insert("INSERT INTO student (user_id, student_no, name, class_name, phone, max_borrow) VALUES (#{userId}, #{studentNo}, #{name}, #{className}, #{phone}, #{maxBorrow})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);
    
    @Update("UPDATE student SET name = #{name}, class_name = #{className}, phone = #{phone}, max_borrow = #{maxBorrow} WHERE id = #{id}")
    int update(Student student);
    
    @Delete("DELETE FROM student WHERE id = #{id}")
    int delete(Long id);
    
    @Select("SELECT COUNT(*) FROM student")
    int count();
}
