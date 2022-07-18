package com.xiaotu.mybatisplus.poji;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@TableName("t_user")
public class User {
    //@TableId(value = "id",type = IdType.AUTO)
    private Long id;
    //@TableField("username")
    private String name;
    private Integer age;
    private String email;

    @TableLogic
    private Integer isDeleted;

    public User(Long id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}
