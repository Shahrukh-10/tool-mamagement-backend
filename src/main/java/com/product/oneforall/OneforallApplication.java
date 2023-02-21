package com.product.oneforall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class },scanBasePackages = "com/product/oneforall")
@EnableMongoRepositories
public class OneforallApplication {
	public static void main(String[] args) {
		SpringApplication.run(OneforallApplication.class, args);
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowCredentials(true).allowedOrigins("*").allowedMethods("*");
//			}
//		};
//	}
}
