# 图书管理系统（Library Management System）

基于 Spring Boot + MyBatis + MySQL 实现的 Web 图书管理系统，支持管理员与学生两类角色，涵盖图书、分类、学生、借阅的全流程管理以及借阅数据统计。前端采用原生 HTML/CSS/JavaScript 构建，无需额外构建工具。

## 技术栈

| 类别 | 技术 |
| --- | --- |
| 语言 | Java 17 |
| 框架 | Spring Boot 3.2.0、Spring Web、Spring Validation |
| 持久层 | MyBatis 3.0.3（注解式 SQL） |
| 数据库 | MySQL 8.0 |
| 工具 | Lombok |
| 前端 | 原生 HTML / CSS / JavaScript |
| 构建 | Maven |
| 认证 | 基于 HttpSession + 拦截器 |

## 功能特性

### 管理员
- 图书管理：新增、编辑、删除、按关键字（书名/作者/ISBN）搜索、按分类查询
- 图书分类管理：分类的增删改查、查看各分类图书数量
- 学生管理：新增、编辑、删除学生，启用/禁用账号，重置学生密码
- 借阅管理：查看全部/在借/超期借阅记录，办理归还
- 数据统计：图书种类数、馆藏总量、学生人数、借阅总量、当前在借量、近 12 个月借阅趋势、借阅排行榜、热门图书排行
- 修改个人密码

### 学生
- 浏览与搜索图书、按分类查看图书
- 在线借书、归还
- 查看个人借阅记录与当前在借记录
- 查看个人信息、修改密码

## 项目结构

```
library-management-system/
├── pom.xml                         # Maven 依赖配置
├── sql/
│   └── library_db.sql              # 数据库建表与初始化脚本
├── doc/                            # 需求文档与系统截图
└── src/main/
    ├── java/com/library/
    │   ├── LibraryApplication.java # 启动类
    │   ├── config/                 # 登录拦截器、Web 配置
    │   ├── controller/             # REST 接口层
    │   ├── service/                # 业务接口
    │   │   └── impl/               # 业务实现
    │   ├── mapper/                 # MyBatis Mapper（注解式 SQL）
    │   ├── entity/                 # 实体类
    │   └── util/                   # Result 统一响应、密码工具
    └── resources/
        ├── application.yml         # 应用与数据库配置
        └── static/                 # 前端页面
            ├── login.html          # 登录页
            ├── admin/              # 管理员端页面
            └── student/            # 学生端页面
```

## 数据库设计

数据库名：`library_db`，共 6 张表：

| 表名 | 说明 |
| --- | --- |
| `user` | 用户表（用户名、密码、角色 ADMIN/STUDENT、状态） |
| `admin` | 管理员信息表（关联 user） |
| `student` | 学生信息表（学号、姓名、班级、最大借阅数等，关联 user） |
| `book` | 图书表（编号、书名、作者、出版社、ISBN、分类、总量、可借量） |
| `book_category` | 图书分类表 |
| `borrow_record` | 借阅记录表（借书/应还/归还时间、状态 0 借阅中 / 1 已归还 / 2 已超期） |

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 1. 初始化数据库
创建数据库并导入脚本（脚本中已包含建表语句与示例数据）：

```sql
CREATE DATABASE library_db DEFAULT CHARACTER SET utf8mb4;
```

```bash
mysql -u root -p library_db < sql/library_db.sql
```

### 2. 修改配置
按本地环境修改 `src/main/resources/application.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: 123456   # 改为你的数据库密码
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

或先打包再运行：

```bash
mvn clean package
java -jar target/library-management-system-1.0.0.jar
```

### 4. 访问系统
启动后访问：<http://localhost:8080/login.html>

## 默认账号

| 角色 | 用户名 | 密码 |
| --- | --- | --- |
| 管理员 | `admin` | `admin123` |
| 学生 | `stu001` | `admin123` |
| 学生 | `stu002` | `admin123` |

> 以上为初始化脚本中的示例账号，密码均为 `admin123`，可登录后在「修改密码」中更改。

## 借阅业务规则

- 默认借期为 **30 天**（应还时间 = 借书时间 + 30 天）。
- 每位学生有最大可借数量（`max_borrow`，默认 5 本），达到上限不能继续借阅。
- 存在**超期未还**图书时，无法借阅新书，需先归还。
- 借书时图书可借数量自动减 1，归还时自动加 1（事务保证一致性）。
- 查询超期记录时会自动将到期未还的记录状态更新为「已超期」。

## API 概览

所有接口统一返回 `{ code, message, data }` 结构；除登录/登出外，`/api/**` 均需登录，部分接口需管理员权限。

| 模块 | 主要接口 |
| --- | --- |
| 认证 | `POST /api/auth/login`、`POST /api/auth/logout`、`GET /api/auth/info`、`POST /api/auth/password` |
| 图书 | `GET /api/book/list`、`GET /api/book/search`、`GET /api/book/category/{id}`、`GET /api/book/hot`、`POST /api/book/add`、`PUT /api/book/{id}`、`DELETE /api/book/{id}` |
| 分类 | `GET /api/category/list`、`GET /api/category/listWithCount`、`POST /api/category/add`、`PUT /api/category/{id}`、`DELETE /api/category/{id}` |
| 学生 | `GET /api/student/list`、`GET /api/student/search`、`GET /api/student/profile`、`POST /api/student/add`、`PUT /api/student/{id}`、`DELETE /api/student/{id}`、`PUT /api/student/{id}/status`、`PUT /api/student/{id}/password` |
| 借阅 | `GET /api/borrow/list`、`GET /api/borrow/current`、`GET /api/borrow/overdue`、`GET /api/borrow/my`、`POST /api/borrow/borrow`、`POST /api/borrow/return/{id}` |
| 统计 | `GET /api/statistics/dashboard`、`GET /api/statistics/monthly`、`GET /api/statistics/topBorrowers`、`GET /api/statistics/hotBooks` |

## 系统截图

系统运行截图见 [`doc/`](doc/) 目录。

## 说明

- 用户密码使用 SHA-256 + Base64 摘要存储（见 `util/PasswordUtil.java`）。
- 本项目为教学/课程设计用途，认证与安全机制较为简化，部署到生产环境前建议加固（如更换为更安全的密码哈希算法、完善权限校验等）。
