package com.xiaotu.mybatisplus;

import com.xiaotu.mybatisplus.poji.User;
import com.xiaotu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetCount(){
        long count = userService.count();
        System.out.println("总记录数：" +  count);
    }

    @Test
    public void testSaveBatch(){

        ArrayList<User> users = new ArrayList<>();
        for (int i=0;i<5;i++){
            User user = new User();
            user.setName("abc"+i);
            user.setAge(20+i);
            users.add(user);
        }
        userService.saveBatch(users);
    }

    @Test
    public void testSaveSingle(){
        User user = new User();
        user.setName("abc");
        user.setAge(20);
        userService.save(user);
    }


}
