package com.test.testpl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(scanBasePackages = "com.test.testpl.*")
@MapperScan({"com.test.testpl.Dao.mapper"})
@CrossOrigin
public class TestPlApplication {

    public static void main(String[] args) {

        SpringApplication.run(TestPlApplication.class, args);
        System.out.println("cdfvfdbgfklbgb");
    }

}
