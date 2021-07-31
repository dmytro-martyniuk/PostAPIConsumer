package com.dmartyni.postconsumer;

import com.dmartyni.postconsumer.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
public class PostApiConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostApiConsumerApplication.class, args);
	}

	@Autowired
	private PostService postService;

	@Bean
	public CommandLineRunner run() {
		return args -> {
			postService.writePosts();
		};
	}

}
