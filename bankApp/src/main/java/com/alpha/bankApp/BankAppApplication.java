package com.alpha.bankApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BankAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankAppApplication.class, args);	
		char r=14+13;
		System.out.println(r);
	}
}