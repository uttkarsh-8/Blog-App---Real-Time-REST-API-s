package com.blog_rest_api.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "CommentDto Model Information")
public class CommentDto {
    @Schema(description = "Comment ID")
    private long id;

    @Schema(description = "Name of the comment author")
    @NotEmpty(message = "Name shall not be empty")
    private String name;

    @Schema(description = "Email of the comment author")
    @NotEmpty(message = "Email shall not be empty")
    @Email(message = "Not a valid email pattern")
    private String email;

    @Schema(description = "Body of the comment")
    @NotEmpty(message = "Body shall not be empty")
    @Size(min = 20, message = "Comment body must have at least 20 characters")
    private String body;
}
