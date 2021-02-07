package com.haiyisoft.excelutils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.haiyisoft")
public class ExcelDownloadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelDownloadApplication.class, args);
    }

}