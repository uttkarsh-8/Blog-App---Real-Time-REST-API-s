package com.blog_rest_api.springbootrestapi.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {
    private String accessToken; // contains the token
    private String tokenType = "Bearer"; // token type to instruct the client that how to use the token
}
