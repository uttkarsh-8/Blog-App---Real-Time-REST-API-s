package com.blog_rest_api.springbootrestapi.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters ")
    private String title;
    @NotEmpty
    @Size(min = 20, message = "Post description should have at least 20 characters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
    private long categoryId;
}
