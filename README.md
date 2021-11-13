


##技术栈
- Java 8
- Spring Boot, Spring MVC, Spring Data JDBC
- MySQL


##开发工具
- IDEA, Postman, Navicat, VS Code


##MySQL 数据库
创建 tinyblog 库，然后创建 blog 表

````
-- 创建 tinyblog 库
CREATE DATABASE `tinyblog` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

-- 创建 blog 表
CREATE TABLE `blog`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NULL,
  `description` varchar(255) NULL,
  `status` int(255) NULL,
  `content` varchar(255) NULL,
  PRIMARY KEY (`id`)
);
````
