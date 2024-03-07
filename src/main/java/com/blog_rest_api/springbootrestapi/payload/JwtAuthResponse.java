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
@Schema(description = "JWT Authentication Response containing the access token and its type")
public class JwtAuthResponse {
    @Schema(description = "Access token for authentication")
    private String accessToken;

    @Schema(description = "Type of the token, instructing the client on how to use the token")
    private String tokenType = "Bearer";
}
