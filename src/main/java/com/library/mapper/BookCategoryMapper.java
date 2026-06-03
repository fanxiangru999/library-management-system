package com.library.mapper;

import com.library.entity.BookCategory;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookCategoryMapper {
    
    @Select("SELECT * FROM book_category ORDER BY create_time DESC")
    List<BookCategory> findAll();
    
    @Select("SELECT * FROM book_category WHERE id = #{id}")
    BookCategory findById(Long id);
    
    @Select("SELECT * FROM book_category WHERE name = #{name}")
    BookCategory findByName(String name);
    
    @Insert("INSERT INTO book_category (name, description) VALUES (#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BookCategory category);
    
    @Update("UPDATE book_category SET name = #{name}, description = #{description} WHERE id = #{id}")
    int update(BookCategory category);
    
    @Delete("DELETE FROM book_category WHERE id = #{id}")
    int delete(Long id);
    
    @Select("SELECT c.*, COUNT(b.id) as book_count FROM book_category c LEFT JOIN book b ON c.id = b.category_id GROUP BY c.id ORDER BY c.create_time DESC")
    @Results({
        @Result(property = "bookCount", column = "book_count")
    })
    List<BookCategory> findAllWithBookCount();
}
