package com.library.mapper;

import com.library.entity.BorrowRecord;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

@Mapper
public interface BorrowRecordMapper {
    
    @Select("SELECT br.*, s.student_no, s.name as student_name, b.title as book_title, b.book_no FROM borrow_record br LEFT JOIN student s ON br.student_id = s.id LEFT JOIN book b ON br.book_id = b.id ORDER BY br.borrow_time DESC")
    @Results({
        @Result(property = "student.studentNo", column = "student_no"),
        @Result(property = "student.name", column = "student_name"),
        @Result(property = "book.title", column = "book_title"),
        @Result(property = "book.bookNo", column = "book_no")
    })
    List<BorrowRecord> findAll();
    
    @Select("SELECT br.*, s.student_no, s.name as student_name, b.title as book_title, b.book_no FROM borrow_record br LEFT JOIN student s ON br.student_id = s.id LEFT JOIN book b ON br.book_id = b.id WHERE br.student_id = #{studentId} ORDER BY br.borrow_time DESC")
    @Results({
        @Result(property = "student.studentNo", column = "student_no"),
        @Result(property = "student.name", column = "student_name"),
        @Result(property = "book.title", column = "book_title"),
        @Result(property = "book.bookNo", column = "book_no")
    })
    List<BorrowRecord> findByStudentId(Long studentId);
    
    @Select("SELECT br.*, s.student_no, s.name as student_name, b.title as book_title, b.book_no FROM borrow_record br LEFT JOIN student s ON br.student_id = s.id LEFT JOIN book b ON br.book_id = b.id WHERE br.student_id = #{studentId} AND br.status = 0 ORDER BY br.borrow_time DESC")
    @Results({
        @Result(property = "student.studentNo", column = "student_no"),
        @Result(property = "student.name", column = "student_name"),
        @Result(property = "book.title", column = "book_title"),
        @Result(property = "book.bookNo", column = "book_no")
    })
    List<BorrowRecord> findCurrentByStudentId(Long studentId);
    
    @Select("SELECT br.*, s.student_no, s.name as student_name, b.title as book_title, b.book_no FROM borrow_record br LEFT JOIN student s ON br.student_id = s.id LEFT JOIN book b ON br.book_id = b.id WHERE br.status = 0 ORDER BY br.borrow_time DESC")
    @Results({
        @Result(property = "student.studentNo", column = "student_no"),
        @Result(property = "student.name", column = "student_name"),
        @Result(property = "book.title", column = "book_title"),
        @Result(property = "book.bookNo", column = "book_no")
    })
    List<BorrowRecord> findAllCurrent();
    
    @Select("SELECT br.*, s.student_no, s.name as student_name, b.title as book_title, b.book_no FROM borrow_record br LEFT JOIN student s ON br.student_id = s.id LEFT JOIN book b ON br.book_id = b.id WHERE br.status = 2 OR (br.status = 0 AND br.due_time < NOW()) ORDER BY br.borrow_time DESC")
    @Results({
        @Result(property = "student.studentNo", column = "student_no"),
        @Result(property = "student.name", column = "student_name"),
        @Result(property = "book.title", column = "book_title"),
        @Result(property = "book.bookNo", column = "book_no")
    })
    List<BorrowRecord> findOverdue();
    
    @Select("SELECT * FROM borrow_record WHERE id = #{id}")
    BorrowRecord findById(Long id);
    
    @Select("SELECT COUNT(*) FROM borrow_record WHERE student_id = #{studentId} AND status = 0")
    int countCurrentByStudentId(Long studentId);
    
    @Select("SELECT COUNT(*) FROM borrow_record WHERE student_id = #{studentId} AND status = 0 AND due_time < NOW()")
    int countOverdueByStudentId(Long studentId);
    
    @Insert("INSERT INTO borrow_record (student_id, book_id, borrow_time, due_time, status) VALUES (#{studentId}, #{bookId}, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(BorrowRecord record);
    
    @Update("UPDATE borrow_record SET return_time = NOW(), status = 1 WHERE id = #{id}")
    int returnBook(Long id);
    
    @Update("UPDATE borrow_record SET status = 2 WHERE status = 0 AND due_time < NOW()")
    int updateOverdueStatus();
    
    @Select("SELECT COUNT(*) FROM borrow_record")
    int count();
    
    @Select("SELECT COUNT(*) FROM borrow_record WHERE status = 0")
    int countCurrent();
    
    @Select("SELECT DATE_FORMAT(borrow_time, '%Y-%m') as month, COUNT(*) as count FROM borrow_record WHERE borrow_time >= DATE_SUB(NOW(), INTERVAL 12 MONTH) GROUP BY DATE_FORMAT(borrow_time, '%Y-%m') ORDER BY month")
    List<Map<String, Object>> monthlyStatistics();
    
    @Select("SELECT s.name, s.student_no, COUNT(br.id) as borrow_count FROM student s LEFT JOIN borrow_record br ON s.id = br.student_id GROUP BY s.id ORDER BY borrow_count DESC LIMIT #{limit}")
    List<Map<String, Object>> topBorrowers(int limit);
}
