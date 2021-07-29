package com.dmartyni.postconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class PostApiConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostApiConsumerApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(PostApiConsumerApplication.class);


}
