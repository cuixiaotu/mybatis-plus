package com.xiaotu.mybatisplus;

import com.xiaotu.mybatisplus.enums.SexEnum;
import com.xiaotu.mybatisplus.mapper.UserMapper;
import com.xiaotu.mybatisplus.poji.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusEnumsTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testSexEnum(){
        User user = new User();
        user.setName("Enum");
        user.setAge(20);
        user.setSex(SexEnum.MALE);
        user.setEmail("et@qq.com");
        userMapper.insert(user);
    }
}
