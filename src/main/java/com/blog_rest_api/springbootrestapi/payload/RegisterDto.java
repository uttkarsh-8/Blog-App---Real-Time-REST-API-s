package com.blog_rest_api.springbootrestapi.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Registration Data Transfer Object for new user sign-up")
public class RegisterDto {
    @Schema(description = "Full name of the new user")
    private String name;

    @Schema(description = "Username for the new user account")
    private String username;

    @Schema(description = "Email address of the new user")
    private String email;

    @Schema(description = "Password for the new user account")
    private String password;
}
