package com.lorne.http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class HttpServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(HttpServerApplication.class, args);
	}


}
