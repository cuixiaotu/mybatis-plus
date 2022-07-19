package com.xiaotu.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaotu.mybatisplus.mapper.UserMapper;
import com.xiaotu.mybatisplus.poji.User;
import com.xiaotu.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;


@Service
@DS("slave_1")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
