#  一、MyBatis-Plus简介

> 第一章摘自https://baomidou.com/pages/24112f，新手还是手打增强印象！

## 1、简介

MyBatis-plus(简称MP)是一个MyBatis的增强工具，在MyBatis的基础上只做增强不做改变，为简化开发、提高效率而生。



## 2、特性

- 无侵入：只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑
- 损耗小：启动即会自动注入基本的CURD，性能基本无损耗，直接面对对象操作
- 强大的CURD操作：内置通用的Mapper，通用的Service，仅仅通过少量配置即可实现单表大部分的CURD操作，更有强大的条件构造器，满足各类使用需求
- 支持Lambda形式调用：通过Lambda表达式，方便的编写各类查询条件，无需再担心字段写错
- 支持主键自动生成：支持多达四种主键策略（内含分布式唯一ID生成器-Sequence），可自由配置，完美解决主键问题
- 支持ActiveRecord模式：支持ActiveRecord形式调用，实体类只需继承Model类即可进行强大的CURD操作
- 支持自定义全局通过操作：支持全局通用方法注入（Write Once,use anywhere）
- 内置代码生成器：采用代码或Maven插件可快速生成Mapper、Model、Service、Controller层代码，支持模板引擎，更有超多的自定义配置等您来使用
- 内置分页插件：基于Mybatis物流分页，开发者无需关心具体操作，配置好插件之后，写分页等同于普通的List查询
- 分页插件支持多种数据库：支持Mysql、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer等多种数据库
- 内置性能分析插件：可输出SQL语句以及其执行时间，建议开发测试是启用该功能，快速定位出慢查询
- 内置全局拦截插件：提供全表delete、update操作智能分析阻断、也可自定义拦截规则，预防误操作

## 3、支持的数据库

常规数据库：Mysql，Oracle，PostgreSQL，OceanBase

更多查看官网



## 4、框架结构

![framework](images/readme/mybatis-plus-framework.jpg)



# 二、入门案例

## 1、开发环境

IDE：idea 2020.2

JDK：1.8_332

构建工具：3.5.4

Mysql版本：8.0.12

Spring Boot:2.7.1

MyBatis-Plus:3.3.1



## 2、创数据库和表

创建表

```mysql
CREATE DATABASE `mybatis_plus` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
use `mybatis_plus`;
CREATE TABLE `user` (
`id` bigint(20) NOT NULL COMMENT '主键ID',
`name` varchar(30) DEFAULT NULL COMMENT '姓名',
`age` int(11) DEFAULT NULL COMMENT '年龄',
`email` varchar(50) DEFAULT NULL COMMENT '邮箱',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

添加数据

```mysql
INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');
```

## 3、创建Spring Boot 工程

- 选择Spring Initializr 
- 引入依赖

```xml
<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
			<version>3.3.1</version>
		</dependency>

		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.24</version>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.28</version>
		</dependency>
	</dependencies>
```

- idea中安装lombok插件



## 4、编写代码

- 配置application.yml( 不同版本的驱动、url配置不同，太常见的几个，百度就知)

```yaml
spring:
  # 配置数据源信息
  datasource:
    # 配置数据源类型
    type: com.zaxxer.hikari.HikariDataSource
    # 配置连接数据库的各个信息
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/mybatis_plus?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456
```

- 启动类

> 在Spring Boot启动类中添加@MapperScan注解，扫描mapper包

```java
@SpringBootApplication
@MapperScan("com.xiaotu.mybaisplus.mapper")
public class MybatisPlusDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusDemoApplication.class, args);
	}

}
```

- 添加实体

```java
@Data //lombok注解
public class User {
  private Long id;
  private String name;
  private Integer age;
  private String email;
}
```

User编译后即可增加各类方法

- 添加mapper

  > BaseMapper是MyBatis-Plus提供的模板mapper，其中包含基本的CRUD方法，泛型为操作的实体类型

```java
public interface UserMapper extends BaseMapper<User> {
}
```

- 测试

```



```







