package com.xiaotu.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xiaotu.mybatisplus.mapper.UserMapper;
import com.xiaotu.mybatisplus.poji.User;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test01(){
        //查询用户名包含a,年龄在20至30岁之前，并且邮箱不为null的用户信息
        //select id,username as name,age,email,is_deleted from t_user where
        // is_deleted=0 and (username like ? and age between ? and ? and email is not null )

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","a")
                .between("age",20,30)
                .isNotNull("email");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test02(){
        //按年龄降序查询用户，如果年龄相同则按id升序排列
        //select id,username as name,age,email,is_deleted from t_user  where is_deleted=0  order by age desc ,id asc

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test03(){
        //删除email为空的用户
        //DELETE FROM t_user WHERE (email IS NULL)

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        int result  = userMapper.delete(queryWrapper);
        System.out.println("受影响的行数：" + result);
    }


    @Test
    public void test04(){
        //将（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息修改
        //UPDATE t_user SET age=?, email=? WHERE (username LIKE ? AND age > ? OR email IS NULL)

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","a")
                .gt("age",20)
                .or()
                .isNull("email");
        User user = new User();
        user.setAge(18);
        user.setEmail("cuixiaotu@gmail.com");
        int result = userMapper.update(user,queryWrapper);
        System.out.println("受影响的行数：" + result);
    }

    @Test
    public void test04b(){
        //将用户名中包含有a（年龄大于20或或邮箱为null）的用户信息修改
        //UPDATE t_user SET age=?, email=? WHERE (username LIKE ? AND (age > ? OR email IS NULL))

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username","a")
                .and(i->i.gt("age",20).or().isNull("email"));

        User user = new User();
        user.setAge(18);
        user.setEmail("cuixiaotub@gmail.com");
        int result = userMapper.update(user,queryWrapper);
        System.out.println("受影响的行数：" + result);
    }


    @Test
    public void test05(){
        //查询用户信息的username和age字段
        //SELECT username,age FROM t_user

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("username","age");
        //selectMaps()返回Map集合列表，通常配合select()使用，避免User对象中没有被查询到的列值为null
        List<Map<String,Object>> map = userMapper.selectMaps(queryWrapper);
        map.forEach(System.out::println);
    }


    @Test
    public void test06(){
        //查询id小于等于3的用户信息
        //SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (id IN (select id from t_user where id <= 3))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id","select id from t_user where id < 30");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void test07(){
        //将（年龄大于20或邮箱为null）并且用户名中包含有a的用户信息修改
        //组装set子句以及修改条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("age",18)
                .set("email","cuixinpk@gmail.com")
                .like("username","a")
                .and(i->i.gt("age",20).or().isNull("email"));
        //这里必须要创建User对象，否则无法应用自动填充。如果没有自动填充，可以设置为null
        //UPDATE t_user SET username=?, age=?,email=? WHERE (username LIKE ? AND (age > ? OR email IS NULL))
        //User user = new User();
        //user.setName("张三");
        //int result = userMapper.update(user, updateWrapper);
        //UPDATE t_user SET age=?,email=? WHERE (username LIKE ? AND (age > ? OR email IS NULL))

        int result = userMapper.update(null,updateWrapper);
        System.out.println(result);
    }

    @Test
    public void test08(){
        String username = null;
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //StringUtils.isNotBlank()判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username","a");
        }
        if (ageBegin != null){
            queryWrapper.ge("age",ageBegin);
        }
        if (ageEnd != null){
            queryWrapper.le("age",ageEnd);
        }
        //SELECT id,username AS name,age,email,is_deleted FROM t_user WHERE (age >=? AND age <= ?)
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void test08UseCondition(){
        //定义查询条件，有可能为null（用户未输入或未选择）
        String username = null;
        Integer ageBegin = 10;
        Integer ageEnd = 24;

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),"username","a")
                .ge(ageBegin!=null,"age",ageBegin)
                .le(ageEnd!=null,"age",ageEnd);

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void test09(){
        //定义查询条件，有可能为null（用户未输入）
        String username = "i";
        Integer ageBegin = 10;
        Integer ageEnd = 24;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //避免使用字符串标识字符，防止运行时错误
        queryWrapper.like(StringUtils.isNotBlank(username),User::getName,username)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .ge( ageEnd != null, User::getAge, ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }


    @Test
    public void test10(){
        LambdaUpdateWrapper<User>  updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAge,18)
                .set(User::getEmail,"cuixinpk@gmail.com")
                .like(User::getName,"i")
                .and( i->i.lt(User::getAge,24).or().isNull(User::getEmail));
        User user = new User();
        int result = userMapper.update(user,updateWrapper);
        System.out.println("受影响的行数：" + result);
    }








}
