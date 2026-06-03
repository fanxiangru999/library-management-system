package com.library.mapper;

import com.library.entity.Book;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface BookMapper {
    
    @Select("SELECT b.*, c.name as category_name FROM book b LEFT JOIN book_category c ON b.category_id = c.id ORDER BY b.create_time DESC")
    @Results({
        @Result(property = "category.name", column = "category_name")
    })
    List<Book> findAll();
    
    @Select("SELECT b.*, c.name as category_name FROM book b LEFT JOIN book_category c ON b.category_id = c.id WHERE b.id = #{id}")
    @Results({
        @Result(property = "category.name", column = "category_name")
    })
    Book findById(Long id);
    
    @Select("SELECT * FROM book WHERE book_no = #{bookNo}")
    Book findByBookNo(String bookNo);
    
    @Select("SELECT b.*, c.name as category_name FROM book b LEFT JOIN book_category c ON b.category_id = c.id WHERE b.title LIKE CONCAT('%', #{keyword}, '%') OR b.author LIKE CONCAT('%', #{keyword}, '%') OR b.isbn LIKE CONCAT('%', #{keyword}, '%') ORDER BY b.create_time DESC")
    @Results({
        @Result(property = "category.name", column = "category_name")
    })
    List<Book> search(String keyword);
    
    @Select("SELECT b.*, c.name as category_name FROM book b LEFT JOIN book_category c ON b.category_id = c.id WHERE b.category_id = #{categoryId} ORDER BY b.create_time DESC")
    @Results({
        @Result(property = "category.name", column = "category_name")
    })
    List<Book> findByCategory(Long categoryId);
    
    @Insert("INSERT INTO book (book_no, title, author, publisher, isbn, category_id, total_count, available_count) VALUES (#{bookNo}, #{title}, #{author}, #{publisher}, #{isbn}, #{categoryId}, #{totalCount}, #{availableCount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Book book);
    
    @Update("UPDATE book SET title = #{title}, author = #{author}, publisher = #{publisher}, isbn = #{isbn}, category_id = #{categoryId}, total_count = #{totalCount}, available_count = #{availableCount}, update_time = NOW() WHERE id = #{id}")
    int update(Book book);
    
    @Delete("DELETE FROM book WHERE id = #{id}")
    int delete(Long id);
    
    @Update("UPDATE book SET available_count = available_count - 1, update_time = NOW() WHERE id = #{id} AND available_count > 0")
    int decreaseAvailableCount(Long id);
    
    @Update("UPDATE book SET available_count = available_count + 1, update_time = NOW() WHERE id = #{id}")
    int increaseAvailableCount(Long id);
    
    @Select("SELECT COUNT(*) FROM book")
    int count();
    
    @Select("SELECT SUM(total_count) FROM book")
    Integer totalBooks();
    
    @Select("SELECT b.*, c.name as category_name, COUNT(br.id) as borrow_count FROM book b LEFT JOIN book_category c ON b.category_id = c.id LEFT JOIN borrow_record br ON b.id = br.book_id GROUP BY b.id ORDER BY borrow_count DESC LIMIT #{limit}")
    @Results({
        @Result(property = "category.name", column = "category_name"),
        @Result(property = "borrowCount", column = "borrow_count")
    })
    List<Book> findHotBooks(int limit);
}
