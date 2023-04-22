package com.hhf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.hhf.mapper")
public class VaccinumApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccinumApplication.class, args);
    }

}
