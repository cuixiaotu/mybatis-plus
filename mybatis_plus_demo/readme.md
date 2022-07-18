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

```java
@SpringBootTest
public class MybatisPlusTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
        userMapper.selectList(null).forEach(System.out::println);
    }

}
```

- 添加日志

在application.yml中

```
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```



# 三、基本的CRUD

## 1、BaseMapper

MyBatis-Plus中的基本CRUD在内置的BaseMapper中都已得到了实现，我们可以直接使用，接口如下：

```java
public interface BaseMapper<T> extends Mapper<T> {
	
   /**
  	* 插入一条记录
  	* @param entity 实体对象
  	*/
    int insert(T entity);

    /**
  	* 根据ID删除
  	* @param id 主键ID
  	*/
    int deleteById(Serializable id);

    /**
  	* 根据ID删除
  	* @param entity 实体对象
  	*/
    int deleteById(T entity);

    /**
  	* 根据columnMap条件删除
  	* @param columnMap 表字段 map对象
  	*/
    int deleteByMap(@Param("cm") Map<String, Object> columnMap);

    /**
  	* 根据entity条件删除记录
  	* @param queryWrapper实体对象封装操作类（可以为null，里面的entity用于生where语句）
  	*/
    int delete(@Param("ew") Wrapper<T> queryWrapper);

    /**
  	* 根据ID批量删除
  	* @param idList 不能为null或empty
  	*/
    int deleteBatchIds(@Param("coll") Collection<?> idList);

    /**
  	* 根据ID修改
  	* @param entity实体对象
  	*/
    int updateById(@Param("et") T entity);

    /**
  	 * 根据 whereEntity 条件，更新记录
  	 * @param entity    实体对象 (set 条件值,可以为 null)
  	 * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成
where 语句）
  	 */
    int update(@Param("et") T entity, @Param("ew") Wrapper<T> updateWrapper);

    /**
  	* 根据 ID 查询
  	* @param id 主键ID
  	*/
    T selectById(Serializable id);

    /**
  	* 查询（根据ID 批量查询）
  	* @param idList 主键ID列表(不能为 null 以及 empty)
  	*/
    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList);

    /**
  	* 查询（根据 columnMap 条件）
  	* @param columnMap 表字段 map 对象
  	*/
    List<T> selectByMap(@Param("cm") Map<String, Object> columnMap);
    
	/**
    * 根据 entity 条件，查询一条记录
  	* 查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常
  	* @param queryWrapper 实体对象封装操作类（可以为 null）
  	*/
    default T selectOne(@Param("ew") Wrapper<T> queryWrapper) {
        List<T> ts = this.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(ts)) {
            if (ts.size() != 1) {
                throw ExceptionUtils.mpe("One record is expected, but the query result is multiple records", new Object[0]);
            } else {
                return ts.get(0);
            }
        } else {
            return null;
        }
    }

    /**
    * 根据 Wrapper 条件，查询是否存在记录
  	* @param queryWrapper 实体对象封装操作类（可以为 null）
  	*/
    default boolean exists(Wrapper<T> queryWrapper) {
        Long count = this.selectCount(queryWrapper);
        return null != count && count > 0L;
    }

    /**
    * 根据 Wrapper 条件，查询总记录数
  	* @param queryWrapper 实体对象封装操作类（可以为 null）
  	*/
    Long selectCount(@Param("ew") Wrapper<T> queryWrapper);

    /**
  	* 根据 entity 条件，查询全部记录
  	* @param queryWrapper 实体对象封装操作类（可以为 null）
  	*/
    List<T> selectList(@Param("ew") Wrapper<T> queryWrapper);

    /**
  	* 根据 Wrapper 条件，查询全部记录
  	* @param queryWrapper 实体对象封装操作类（可以为 null）
  	*/
    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper);

    /**
  	* 根据 Wrapper 条件，查询全部记录
  	* <p>注意： 只返回第一个字段的值</p>
  	* @param queryWrapper 实体对象封装操作类（可以为 null）
  	*/
    List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper);

    /**
  	* 根据 entity 条件，查询全部记录（并翻页）
  	* @param page     分页查询条件（可以为 RowBounds.DEFAULT）
  	* @param queryWrapper 实体对象封装操作类（可以为 null）
  	*/
    <P extends IPage<T>> P selectPage(P page, @Param("ew") Wrapper<T> queryWrapper);

    /**
  	* 根据 Wrapper 条件，查询全部记录（并翻页）
  	* @param page     分页查询条件
  	* @param queryWrapper 实体对象封装操作类
  	*/
    <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, @Param("ew") Wrapper<T> queryWrapper);
}
```



## 2、插入

```java
    @Test
    public void testInsert(){
        User user = new User(null,"张三",18,"zhangsan@qq.com");
        int result = userMapper.insert(user);
        System.out.println("受影响的行数："+result );
        System.out.println("id自动获取:"+user.getId());
    }
```

> id自动获取:1548864157437214722。默认使用了雪花算法



## 3、删除

- 根据ID删除

```java
    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(1548864157437214722L);
        System.out.println("受影响的行数："+result );
    }
```

- 根据Ids删除

```java
    @Test
    public void testDeleteByIds(){
        List<Long> idList = Arrays.asList(1L,2L,3L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("受影响的行数："+result );
    }
```

- 根据map删除

```java
    @Test
    public void testDeleteByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("age",18);
        map.put("name","张三");
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响的行数："+result );
    }
```



## 4、修改

```java
    @Test
    public void testUpdateById(){
        User user = new User(4L,"admin",22,null);
        int result = userMapper.updateById(user);
        System.out.println(result);
    }
```



## 5、查询

- 根据ID查询

```java
@Test
public void testSelectById(){
    User user = userMapper.selectById(4L);
    System.out.println(user);
}
```



- 根据Ids查询

```java
@Test
public void testSelectByIds(){
    List<Long> idList = Arrays.asList(4L,5L);
    List<User> userList = userMapper.selectBatchIds(idList);
    System.out.println(userList);
}
```



- 根据map查询

```java
@Test
public void testSelectByMap(){
    Map<String,Object> map = new HashMap<>();
    map.put("age",22);
    map.put("name","admin");
    List<User> list = userMapper.selectByMap(map);
    System.out.println(list);
}
```



- 查询所有数据

```java
@Test
public void testSelectList(){
    userMapper.selectList(null).forEach(System.out::println);
}
```



## 6、通用的Service

> 说明
>
> - 通用的Service封装了IService接口，进一步封装CRUD采用`get 查询单行` `remove删除` `list查询集合` `page 分页`前缀命名方式区分Mapper层避免混淆
> - 泛型`T`为任意实体对象
> - 建议如果存在自定义通用Service方法的可能，请创建自己的`IBaseService`继承`Mybatis-Plus`提供的基类
> - 官网地址：https://baomidou.com/pages/49cc81/#service-crud-%E6%8E%A5%E5%8F%A3

- IService 

MyBatis-Plus中有一个接口IService和其实现类ServiceImpl，封装了常见的业务层逻辑

- 创建Service接口

```java
//UserService继承IService的基本模板
public interface UserService extend IService<User>{

}
```

- 实现类

```java
package com.xiaotu.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaotu.mybatisplus.mapper.UserMapper;
import com.xiaotu.mybatisplus.poji.User;
import com.xiaotu.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;
/**
* ServiceImpl实现了IService，提供了IService中基础功能的实现
* 若ServiceImpl无法满足业务需求，则可以使用自定的UserService定义方法，并在实现类中实现
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

```

