package com.xiaoao.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.xiaoao.mall.model.dao")
public class mallApplication {

    public static void main(String[] args) {
        SpringApplication.run(mallApplication.class, args);
    }

}
