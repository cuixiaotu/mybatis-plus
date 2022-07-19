package com.xiaotu.mybatisplus.poji;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

@Data
public class Product {
    private long id;
    private String name;
    private Integer price;
    @Version
    private Integer version;
}
