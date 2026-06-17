package com.ban.carbonaccount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ban.carbonaccount.mapper")
public class CarbonAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonAccountApplication.class, args);
    }
}