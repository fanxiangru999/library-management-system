package com.library.service.impl;

import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.Student;
import com.library.mapper.BookMapper;
import com.library.mapper.BorrowRecordMapper;
import com.library.mapper.StudentMapper;
import com.library.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BorrowRecordServiceImpl implements BorrowRecordService {
    
    private final BorrowRecordMapper borrowRecordMapper;
    private final BookMapper bookMapper;
    private final StudentMapper studentMapper;
    
    @Override
    public List<BorrowRecord> findAll() {
        return borrowRecordMapper.findAll();
    }
    
    @Override
    public List<BorrowRecord> findByStudentId(Long studentId) {
        return borrowRecordMapper.findByStudentId(studentId);
    }
    
    @Override
    public List<BorrowRecord> findCurrentByStudentId(Long studentId) {
        return borrowRecordMapper.findCurrentByStudentId(studentId);
    }
    
    @Override
    public List<BorrowRecord> findAllCurrent() {
        return borrowRecordMapper.findAllCurrent();
    }
    
    @Override
    public List<BorrowRecord> findOverdue() {
        borrowRecordMapper.updateOverdueStatus();
        return borrowRecordMapper.findOverdue();
    }
    
    @Override
    @Transactional
    public String borrow(Long studentId, Long bookId) {
        Student student = studentMapper.findById(studentId);
        if (student == null) {
            return "学生不存在";
        }
        
        int currentCount = borrowRecordMapper.countCurrentByStudentId(studentId);
        if (currentCount >= student.getMaxBorrow()) {
            return "已达到最大借阅数量(" + student.getMaxBorrow() + "本)";
        }
        
        int overdueCount = borrowRecordMapper.countOverdueByStudentId(studentId);
        if (overdueCount > 0) {
            return "您有超期未还的图书，请先归还";
        }
        
        Book book = bookMapper.findById(bookId);
        if (book == null) {
            return "图书不存在";
        }
        if (book.getAvailableCount() <= 0) {
            return "该图书已无可借库存";
        }
        
        int rows = bookMapper.decreaseAvailableCount(bookId);
        if (rows <= 0) {
            return "借阅失败，库存不足";
        }
        
        BorrowRecord record = new BorrowRecord();
        record.setStudentId(studentId);
        record.setBookId(bookId);
        borrowRecordMapper.insert(record);
        
        return null;
    }
    
    @Override
    @Transactional
    public String returnBook(Long recordId) {
        BorrowRecord record = borrowRecordMapper.findById(recordId);
        if (record == null) {
            return "借阅记录不存在";
        }
        if (record.getStatus() == 1) {
            return "该图书已归还";
        }
        
        borrowRecordMapper.returnBook(recordId);
        bookMapper.increaseAvailableCount(record.getBookId());
        
        return null;
    }
    
    @Override
    public int count() {
        return borrowRecordMapper.count();
    }
    
    @Override
    public int countCurrent() {
        return borrowRecordMapper.countCurrent();
    }
    
    @Override
    public List<Map<String, Object>> monthlyStatistics() {
        return borrowRecordMapper.monthlyStatistics();
    }
    
    @Override
    public List<Map<String, Object>> topBorrowers(int limit) {
        return borrowRecordMapper.topBorrowers(limit);
    }
}
