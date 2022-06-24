package com.example.date;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.**.mapper"})
public class DateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DateApplication.class, args);
	}

}
