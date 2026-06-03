CREATE DATABASE IF NOT EXISTS library_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE library_db;

DROP TABLE IF EXISTS borrow_record;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS book_category;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码(BCrypt加密)',
    role VARCHAR(20) NOT NULL COMMENT '角色: ADMIN/STUDENT',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) COMMENT '用户表';

CREATE TABLE admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE COMMENT '关联用户ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) COMMENT '联系电话',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
) COMMENT '管理员表';

CREATE TABLE student (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE COMMENT '关联用户ID',
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    class_name VARCHAR(50) COMMENT '班级',
    phone VARCHAR(20) COMMENT '联系方式',
    max_borrow INT DEFAULT 5 COMMENT '最大借阅数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
) COMMENT '学生信息表';

CREATE TABLE book_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称',
    description VARCHAR(200) COMMENT '分类描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT '图书分类表';

CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_no VARCHAR(50) NOT NULL UNIQUE COMMENT '图书编号',
    title VARCHAR(100) NOT NULL COMMENT '书名',
    author VARCHAR(50) COMMENT '作者',
    publisher VARCHAR(100) COMMENT '出版社',
    isbn VARCHAR(20) COMMENT 'ISBN',
    category_id BIGINT COMMENT '分类ID',
    total_count INT DEFAULT 1 COMMENT '总数量',
    available_count INT DEFAULT 1 COMMENT '可借数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES book_category(id)
) COMMENT '图书表';

CREATE TABLE borrow_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL COMMENT '学生ID',
    book_id BIGINT NOT NULL COMMENT '图书ID',
    borrow_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '借书时间',
    due_time DATETIME COMMENT '应还时间',
    return_time DATETIME COMMENT '实际还书时间',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-借阅中, 1-已归还, 2-已超期',
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
) COMMENT '借阅记录表';

INSERT INTO user (username, password, role, status) VALUES 
('admin', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', 'ADMIN', 1);

INSERT INTO admin (user_id, name, phone) VALUES (1, '系统管理员', '13800138000');

INSERT INTO book_category (name, description) VALUES 
('文学', '文学类书籍'),
('计算机', '计算机科学与技术'),
('历史', '历史类书籍'),
('经济', '经济学相关'),
('哲学', '哲学类书籍');

INSERT INTO book (book_no, title, author, publisher, isbn, category_id, total_count, available_count) VALUES
('BK001', 'Java核心技术 卷I', 'Cay S. Horstmann', '机械工业出版社', '9787111547426', 2, 5, 5),
('BK002', 'Java核心技术 卷II', 'Cay S. Horstmann', '机械工业出版社', '9787111547433', 2, 3, 3),
('BK003', 'Spring Boot实战', '克雷格·沃斯', '人民邮电出版社', '9787115453686', 2, 4, 4),
('BK004', '深入理解Java虚拟机', '周志明', '机械工业出版社', '9787111641247', 2, 6, 6),
('BK005', 'MySQL必知必会', 'Ben Forta', '人民邮电出版社', '9787115313980', 2, 5, 5),
('BK006', '算法导论', 'Thomas H.Cormen', '机械工业出版社', '9787111407010', 2, 3, 3),
('BK007', '计算机网络', '谢希仁', '电子工业出版社', '9787121302954', 2, 4, 4),
('BK008', '操作系统概念', 'Abraham Silberschatz', '高等教育出版社', '9787040423020', 2, 3, 3),
('BK009', '红楼梦', '曹雪芹', '人民文学出版社', '9787020002207', 1, 5, 5),
('BK010', '三国演义', '罗贯中', '人民文学出版社', '9787020008728', 1, 4, 4),
('BK011', '水浒传', '施耐庵', '人民文学出版社', '9787020015016', 1, 4, 4),
('BK012', '西游记', '吴承恩', '人民文学出版社', '9787020008735', 1, 5, 5),
('BK013', '百年孤独', '加西亚·马尔克斯', '南海出版公司', '9787544253994', 1, 3, 3),
('BK014', '活着', '余华', '作家出版社', '9787506365437', 1, 6, 6),
('BK015', '围城', '钱钟书', '人民文学出版社', '9787020024759', 1, 4, 4),
('BK016', '中国通史', '吕思勉', '华东师范大学出版社', '9787567528734', 3, 3, 3),
('BK017', '史记', '司马迁', '中华书局', '9787101003048', 3, 4, 4),
('BK018', '万历十五年', '黄仁宇', '中华书局', '9787101054491', 3, 5, 5),
('BK019', '国富论', '亚当·斯密', '商务印书馆', '9787100012461', 4, 3, 3),
('BK020', '经济学原理', '曼昆', '北京大学出版社', '9787301150894', 4, 4, 4);

INSERT INTO user (username, password, role, status) VALUES 
('stu001', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', 'STUDENT', 1),
('stu002', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', 'STUDENT', 1);

INSERT INTO student (user_id, student_no, name, class_name, phone, max_borrow) VALUES
(2, '2024001', '张三', '计算机2401班', '13811111111', 5),
(3, '2024002', '李四', '计算机2401班', '13822222222', 5);
