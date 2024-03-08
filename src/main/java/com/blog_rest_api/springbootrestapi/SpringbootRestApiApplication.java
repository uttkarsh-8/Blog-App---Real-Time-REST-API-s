package com.blog_rest_api.springbootrestapi;

import com.blog_rest_api.springbootrestapi.entity.Role;
import com.blog_rest_api.springbootrestapi.repository.RoleRepositoy;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "REST APIs Documentation-             " +
						"        CUD operations on 'Post' & 'Category' APIs require admin access (no more users with 'admin' access be created ),           " +
						"Admin Credentials - username/name = admin,          " +
						"Password = admin,            " +
						"For sample a testing user is also made which(more users with 'user' role can be created via login api)-,            " +
						"Credentials username/name = tester,            " +
						"password = tester,         " +
						"passing JWT token is necessary for both the user roles.",
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
// implements command line runner is removed at the moment as in local, in local run only once with it 
public class SpringbootRestApiApplication implements CommandLineRunner {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestApiApplication.class, args);
	}

	@Autowired
	RoleRepositoy roleRepositoy;
	@Override
	public void run(String... args) throws Exception {
		createRoleIfNotFound("ROLE_ADMIN");
		createRoleIfNotFound("ROLE_USER");
	}

	private void createRoleIfNotFound(String name) {
		Optional<Role> role = roleRepositoy.findByName(name);
		if (!role.isPresent()) {
			Role newRole = new Role();
			newRole.setName(name);
			roleRepositoy.save(newRole);
		}
	}

}
