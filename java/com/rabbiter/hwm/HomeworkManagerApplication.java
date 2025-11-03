package com.rabbiter.hwm;

import com.rabbiter.hwm.utils.PathUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeworkManagerApplication {

    public static void main(String[] args) {
        System.out.println("ProjectPath : " + PathUtils.getClassLoadRootPath());
        SpringApplication.run(HomeworkManagerApplication.class, args);
    }

}
