package com.blog_rest_api.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Login Data Transfer Object containing login credentials")
public class LoginDto {
    @Schema(description = "Username or Email of the user trying to log in")
    private String usernameOrEmail;

    @Schema(description = "Password of the user trying to log in")
    private String password;
}

