package com.xiaotu.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaotu.mybatisplus.mapper.ProductMapper;
import com.xiaotu.mybatisplus.poji.Product;
import com.xiaotu.mybatisplus.service.ProductService;
import org.springframework.stereotype.Service;

@Service
@DS("master")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
