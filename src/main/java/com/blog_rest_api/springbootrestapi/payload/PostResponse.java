package com.blog_rest_api.springbootrestapi.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//creating this class for support of pagination
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Paginated response for posts including pagination details and post content")
public class PostResponse {

    @Schema(description = "List of posts in the current page")
    private List<PostDto> content;

    @Schema(description = "Current page number")
    private int pageNo;

    @Schema(description = "Number of posts per page")
    private int pageSize;

    @Schema(description = "Total number of posts across all pages")
    private int totalElements;

    @Schema(description = "Total number of available pages")
    private long totalPages;

    @Schema(description = "Boolean flag indicating whether the current page is the last one")
    private boolean last;
}