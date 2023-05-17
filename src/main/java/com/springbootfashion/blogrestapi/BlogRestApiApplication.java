package com.springbootfashion.blogrestapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info =@Info(
				title = "spring boot App REST APIs",
				description = "spring boot blog App REST APIs Documentation",
				version = "v.10",
				contact = @Contact(
						name = "ichebadu",
						email = "jazzmaniche@gmail.com",
						url = "https://www.udemy.com/course/building-real-time-rest-apis-with-spring-boot/learn/lecture/36901014#content"
				),
				license = @License(
						name = "Apache 2.1.0",
						url = "https://www.udemy.com/course/building-real-time-rest-apis-with-spring-boot/learn/lecture/36901014#content"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/decadevs/week-10-task-ichebadu"
		)
)


public class BlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);
	}

}
