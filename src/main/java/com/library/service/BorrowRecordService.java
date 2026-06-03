package com.library.service;

import com.library.entity.BorrowRecord;
import java.util.List;
import java.util.Map;

public interface BorrowRecordService {
    List<BorrowRecord> findAll();
    List<BorrowRecord> findByStudentId(Long studentId);
    List<BorrowRecord> findCurrentByStudentId(Long studentId);
    List<BorrowRecord> findAllCurrent();
    List<BorrowRecord> findOverdue();
    String borrow(Long studentId, Long bookId);
    String returnBook(Long recordId);
    int count();
    int countCurrent();
    List<Map<String, Object>> monthlyStatistics();
    List<Map<String, Object>> topBorrowers(int limit);
}
