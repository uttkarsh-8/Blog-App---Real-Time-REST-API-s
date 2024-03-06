package com.blog_rest_api.springbootrestapi;

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
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Uttkarsh Srivastava",
						email = "uttkarsh.srivastava12@gmail.com",
						url = "https://github.com/uttkarsh-8"
				),
				license = @License(
						name = "Apache 2.0", // meaning not proprietary
						url = "http://www.apache.org/licenses/LICENSE-2.0.html"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Git Repository",
				url = "https://github.com/uttkarsh-8/Blog-App---Real-Time-REST-API-s"
		)
)
public class SpringbootRestApiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestApiApplication.class, args);
	}

}
