package com.freshsales;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.freshsales.mapper")
public class FreshSalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreshSalesApplication.class, args);
    }
}