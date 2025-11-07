package org.example.transcation.week4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Week4Application {

    public static void main(String[] args) {
        SpringApplication.run(Week4Application.class, args);
    }

}
