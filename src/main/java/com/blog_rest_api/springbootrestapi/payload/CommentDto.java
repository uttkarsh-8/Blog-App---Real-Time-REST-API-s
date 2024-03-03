package com.blog_rest_api.springbootrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;
    @NotEmpty(message = "Name shall not be empty")
    private String name;
    @NotEmpty(message = "Email shall not be empty")
    @Email(message = "Not a valid email pattern")
    private String email;
    @NotEmpty(message = "Body shall not be empty")
    @Size(min = 20, message = "Comment body must have at least 20 characters")
    private String body;

}
