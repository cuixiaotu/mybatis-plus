package com.xiaotu.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaotu.mybatisplus.poji.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    /*
    * 根据年龄查询用户列表，分页显示
    * @param page 分页对象，xml中可以从里面进行取值，传递参数page即自动分页，必须放在第一位
    * @param age 年纪
    * @return
    * */
    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);
}
