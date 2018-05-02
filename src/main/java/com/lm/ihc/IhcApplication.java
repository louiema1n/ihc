package com.lm.ihc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lm.ihc.mapper")
public class IhcApplication {

    public static void main(String[] args) {
        SpringApplication.run(IhcApplication.class, args);
    }
}
