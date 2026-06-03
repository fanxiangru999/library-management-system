/*
 Navicat Premium Dump SQL

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : 127.0.0.1:3306
 Source Schema         : library_db

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 03/06/2026 19:23:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 1, '系统管理员', '13800138000', '2026-01-06 11:18:44');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '图书编号',
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '书名',
  `author` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '出版社',
  `isbn` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'ISBN',
  `category_id` bigint NULL DEFAULT NULL COMMENT '分类ID',
  `total_count` int NULL DEFAULT 1 COMMENT '总数量',
  `available_count` int NULL DEFAULT 1 COMMENT '可借数量',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `book_no`(`book_no` ASC) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `book_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '图书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'BK001', 'Java核心技术 卷I22', 'Cay S. Horstmann', '机械工业出版社', '9787111547426', 2, 5, 5, '2026-01-06 11:18:44', '2026-01-09 23:07:19');
INSERT INTO `book` VALUES (2, 'BK002', 'Java核心技术2', 'Cay S. Horstmann', '机械工业出版社', '9787111547433', 2, 3, 3, '2026-01-06 11:18:44', '2026-01-10 22:06:25');
INSERT INTO `book` VALUES (3, 'BK003', 'Spring Boot实战', '克雷格·沃斯', '人民邮电出版社', '9787115453686', 2, 4, 4, '2026-01-06 11:18:44', '2026-01-10 16:18:31');
INSERT INTO `book` VALUES (4, 'BK004', '深入理解Java虚拟机', '周志明', '机械工业出版社', '9787111641247', 2, 6, 5, '2026-01-06 11:18:44', '2026-01-10 16:18:24');
INSERT INTO `book` VALUES (5, 'BK005', 'MySQL必知必会', 'Ben Forta', '人民邮电出版社', '9787115313980', 2, 5, 5, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (6, 'BK006', '算法导论', 'Thomas H.Cormen', '机械工业出版社', '9787111407010', 2, 3, 3, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (7, 'BK007', '计算机网络', '谢希仁', '电子工业出版社', '9787121302954', 2, 4, 3, '2026-01-06 11:18:44', '2026-01-06 11:24:13');
INSERT INTO `book` VALUES (8, 'BK008', '操作系统概念', 'Abraham Silberschatz', '高等教育出版社', '9787040423020', 2, 3, 3, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (9, 'BK009', '红楼梦', '曹雪芹', '人民文学出版社', '9787020002207', 1, 5, 4, '2026-01-06 11:18:44', '2026-01-06 11:24:17');
INSERT INTO `book` VALUES (11, 'BK011', '水浒传', '施耐庵', '人民文学出版社', '9787020015016', 1, 4, 4, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (12, 'BK012', '西游记', '吴承恩', '人民文学出版社', '9787020008735', 1, 5, 5, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (13, 'BK013', '百年孤独', '加西亚·马尔克斯', '南海出版公司', '9787544253994', 1, 3, 3, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (14, 'BK014', '活着', '余华', '作家出版社', '9787506365437', 1, 6, 6, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (15, 'BK015', '围城', '钱钟书', '人民文学出版社', '9787020024759', 1, 4, 4, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (16, 'BK016', '中国通史', '吕思勉', '华东师范大学出版社', '9787567528734', 3, 3, 3, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (17, 'BK017', '史记', '司马迁', '中华书局', '9787101003048', 3, 4, 4, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (18, 'BK018', '万历十五年', '黄仁宇', '中华书局', '9787101054491', 3, 5, 5, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (19, 'BK019', '国富论', '亚当·斯密', '商务印书馆', '9787100012461', 4, 3, 3, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `book` VALUES (20, 'BK020', '经济学原理', '曼昆', '北京大学出版社', '9787301150894', 4, 4, 4, '2026-01-06 11:18:44', '2026-01-06 11:18:44');

-- ----------------------------
-- Table structure for book_category
-- ----------------------------
DROP TABLE IF EXISTS `book_category`;
CREATE TABLE `book_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '分类名称',
  `description` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分类描述',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '图书分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_category
-- ----------------------------
INSERT INTO `book_category` VALUES (1, '文学', '文学类书籍', '2026-01-06 11:18:44');
INSERT INTO `book_category` VALUES (2, '计算机', '计算机科学与技术', '2026-01-06 11:18:44');
INSERT INTO `book_category` VALUES (3, '历史', '历史类书籍', '2026-01-06 11:18:44');
INSERT INTO `book_category` VALUES (4, '经济', '经济学相关', '2026-01-06 11:18:44');
INSERT INTO `book_category` VALUES (5, '哲学', '哲学类书籍', '2026-01-06 11:18:44');

-- ----------------------------
-- Table structure for borrow_record
-- ----------------------------
DROP TABLE IF EXISTS `borrow_record`;
CREATE TABLE `borrow_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `book_id` bigint NOT NULL COMMENT '图书ID',
  `borrow_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '借书时间',
  `due_time` datetime NULL DEFAULT NULL COMMENT '应还时间',
  `return_time` datetime NULL DEFAULT NULL COMMENT '实际还书时间',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态: 0-借阅中, 1-已归还, 2-已超期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id` ASC) USING BTREE,
  INDEX `book_id`(`book_id` ASC) USING BTREE,
  CONSTRAINT `borrow_record_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `borrow_record_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '借阅记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow_record
-- ----------------------------
INSERT INTO `borrow_record` VALUES (1, 1, 1, '2026-01-06 11:19:14', '2026-02-05 11:19:14', '2026-01-06 11:22:07', 1);
INSERT INTO `borrow_record` VALUES (2, 1, 4, '2026-01-06 11:19:17', '2026-02-05 11:19:17', '2026-01-06 11:22:05', 1);
INSERT INTO `borrow_record` VALUES (3, 1, 7, '2026-01-06 11:24:13', '2026-02-05 11:24:13', NULL, 0);
INSERT INTO `borrow_record` VALUES (4, 1, 9, '2026-01-06 11:24:17', '2026-02-05 11:24:17', NULL, 0);
INSERT INTO `borrow_record` VALUES (5, 1, 3, '2026-01-10 10:00:50', '2026-02-09 10:00:50', '2026-01-10 16:18:31', 1);
INSERT INTO `borrow_record` VALUES (6, 1, 4, '2026-01-10 16:18:24', '2026-02-09 16:18:24', NULL, 0);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `student_no` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '学号',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
  `class_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '班级',
  `phone` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `max_borrow` int NULL DEFAULT 5 COMMENT '最大借阅数量',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE,
  UNIQUE INDEX `student_no`(`student_no` ASC) USING BTREE,
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '学生信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 2, '2024001', '张三', '计算机2401班', '13811111111', 5, '2026-01-06 11:18:44');
INSERT INTO `student` VALUES (2, 3, '2024002', '李四', '计算机2401班', '13822222222', 5, '2026-01-06 11:18:44');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码(BCrypt加密)',
  `role` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '角色: ADMIN/STUDENT',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', 'ADMIN', 1, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `user` VALUES (2, 'stu001', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', 'STUDENT', 1, '2026-01-06 11:18:44', '2026-01-06 11:18:44');
INSERT INTO `user` VALUES (3, 'stu002', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', 'STUDENT', 1, '2026-01-06 11:18:44', '2026-01-06 11:18:44');

SET FOREIGN_KEY_CHECKS = 1;
