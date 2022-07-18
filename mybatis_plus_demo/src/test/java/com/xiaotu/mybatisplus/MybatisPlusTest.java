package com.xiaotu.mybatisplus;


import com.xiaotu.mybatisplus.mapper.UserMapper;
import com.xiaotu.mybatisplus.poji.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MybatisPlusTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
        userMapper.selectList(null).forEach(System.out::println);
    }

    @Test
    public void testInsert(){
        User user = new User(null,"张三",18,"zhangsan@qq.com");
        int result = userMapper.insert(user);
        System.out.println("受影响的行数："+result );
        System.out.println("id自动获取:"+user.getId());
    }

    @Test
    public void testDeleteById(){
        int result = userMapper.deleteById(1548864157437214722L);
        System.out.println("受影响的行数："+result );
    }

    @Test
    public void testDeleteByIds(){
        List<Long> idList = Arrays.asList(4L,5L);
        int result = userMapper.deleteBatchIds(idList);
        System.out.println("受影响的行数："+result );
    }

    @Test
    public void testDeleteByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("age",18);
        map.put("name","张三");
        int result = userMapper.deleteByMap(map);
        System.out.println("受影响的行数："+result );
    }


    @Test
    public void testUpdateById(){
        User user = new User(4L,"admin",22,null);
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    @Test
    public void testSelectById(){
        User user = userMapper.selectById(4L);
        System.out.println(user);
    }

    @Test
    public void testSelectByIds(){
        List<Long> idList = Arrays.asList(4L,5L);
        List<User> userList = userMapper.selectBatchIds(idList);
        System.out.println(userList);
    }

    @Test
    public void testSelectByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("age",22);
        map.put("name","admin");
        List<User> list = userMapper.selectByMap(map);
        System.out.println(list);
    }




}
